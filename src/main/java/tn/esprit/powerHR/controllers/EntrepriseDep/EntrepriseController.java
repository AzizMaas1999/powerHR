package tn.esprit.powerHR.controllers.EntrepriseDep;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import org.json.JSONObject;
import javafx.stage.Popup;
import javafx.scene.layout.Region;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Pos;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.stage.FileChooser;
import tn.esprit.powerHR.models.EntrepriseDep.Country;
import tn.esprit.powerHR.models.EntrepriseDep.Entreprise;
import tn.esprit.powerHR.services.EntrepriseDep.EntrepriseService;
import tn.esprit.powerHR.services.EntrepriseDep.SmsVerificationService;

public class EntrepriseController implements Initializable {
    @FXML private TextField searchField;
    @FXML private TextField nomField;
    @FXML private TextField secteurField;
    @FXML private TextField matriculeField;
    @FXML private TextField phoneField;
    @FXML private TextField verificationCodeField;
    @FXML private Button verifyPhoneBtn;
    @FXML private Button submitVerificationBtn;
    @FXML private Button countrySelectBtn;

    @FXML private ListView<Entreprise> entrepriseList;

    private EntrepriseService entrepriseService;
    private Entreprise selectedEntreprise;
    private final SmsVerificationService smsVerificationService = new SmsVerificationService();

    private ObservableList<Entreprise> entrepriseItems;
    private FilteredList<Entreprise> filteredList;
    private List<Country> countries;
    private Country selectedCountry;
    private Popup countryPopup;
    private TextField countrySearchField;
    private ListView<Country> countryListView;

    private boolean isPhoneVerified = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        entrepriseService = new EntrepriseService();
        entrepriseItems = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(entrepriseItems, p -> true);
        entrepriseList.setItems(filteredList);

        // Initialize countries
        countries = Arrays.asList(
                new Country("Tunisia", "+216", "🇹🇳"),
                new Country("Algeria", "+213", "🇩🇿"),
                new Country("Morocco", "+212", "🇲🇦"),
                new Country("Egypt", "+20", "🇪🇬"),
                new Country("Saudi Arabia", "+966", "🇸🇦"),
                new Country("UAE", "+971", "🇦🇪"),
                new Country("Qatar", "+974", "🇶🇦"),
                new Country("Bahrain", "+973", "🇧🇭"),
                new Country("Oman", "+968", "🇴🇲"),
                new Country("Kuwait", "+965", "🇰🇼")
        );

        selectedCountry = countries.get(0); // Default to Tunisia
        setupCountrySelector();

        // Disable verification fields initially
        verificationCodeField.setDisable(true);
        submitVerificationBtn.setDisable(true);

        // Setup search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(entreprise -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return entreprise.getNom().toLowerCase().contains(lowerCaseFilter) ||
                        entreprise.getSecteur().toLowerCase().contains(lowerCaseFilter) ||
                        entreprise.getMatriculeFiscale().toLowerCase().contains(lowerCaseFilter) ||
                        (entreprise.getPhoneNumber() != null &&
                                entreprise.getPhoneNumber().toLowerCase().contains(lowerCaseFilter));
            });
        });

        // Setup list cell factory for custom display
        entrepriseList.setCellFactory(lv -> new ListCell<Entreprise>() {
            @Override
            protected void updateItem(Entreprise entreprise, boolean empty) {
                super.updateItem(entreprise, empty);
                if (empty || entreprise == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox container = new VBox(5);
                    container.setPadding(new Insets(5, 10, 5, 10));

                    Label nameLabel = new Label(entreprise.getNom());
                    nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                    Label detailsLabel = new Label(String.format("Secteur: %s | Matricule: %s",
                            entreprise.getSecteur(), entreprise.getMatriculeFiscale()));
                    detailsLabel.setStyle("-fx-text-fill: #666666;");

                    if (entreprise.getPhoneNumber() != null) {
                        Label phoneStatus = new Label("☎ " + entreprise.getPhoneNumber());
                        phoneStatus.setStyle(entreprise.isPhoneVerified() ?
                                "-fx-text-fill: #28a745;" : "-fx-text-fill: #dc3545;");
                        container.getChildren().add(phoneStatus);
                    }

                    container.getChildren().addAll(nameLabel, detailsLabel);
                    setGraphic(container);
                }
            }
        });

        entrepriseList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedEntreprise = newSelection;
                populateFields(newSelection);
            }
        });

        refreshList();
    }

    private void setupCountrySelector() {
        // Create country select button
        countrySelectBtn = new Button();
        updateCountryButtonText();
        countrySelectBtn.getStyleClass().add("country-select-btn");

        // Create popup content
        VBox popupContent = new VBox(10);
        popupContent.getStyleClass().add("country-popup");
        popupContent.setPadding(new Insets(10));

        // Create search field
        countrySearchField = new TextField();
        countrySearchField.setPromptText("Search country...");
        countrySearchField.getStyleClass().add("country-search");

        // Create country list
        countryListView = new ListView<>();
        countryListView.setItems(FXCollections.observableArrayList(countries));
        countryListView.setPrefHeight(200);
        countryListView.getStyleClass().add("country-list");

        // Setup cell factory
        countryListView.setCellFactory(lv -> new ListCell<Country>() {
            @Override
            protected void updateItem(Country country, boolean empty) {
                super.updateItem(country, empty);
                if (empty || country == null) {
                    setText(null);
                } else {
                    setText(country.getFlag() + " " + country.getName() + " (" + country.getCode() + ")");
                }
            }
        });

        // Add search functionality
        countrySearchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String search = newVal.toLowerCase();
            countryListView.setItems(FXCollections.observableArrayList(
                    countries.stream()
                            .filter(c -> c.getName().toLowerCase().contains(search) ||
                                    c.getCode().toLowerCase().contains(search))
                            .collect(Collectors.toList())
            ));

        });

        // Handle selection
        countryListView.setOnMouseClicked(e -> {
            Country selected = countryListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selectedCountry = selected;
                updateCountryButtonText();
                countryPopup.hide();
            }
        });

        // Create popup
        popupContent.getChildren().addAll(countrySearchField, countryListView);
        countryPopup = new Popup();
        countryPopup.getContent().add(popupContent);
        countryPopup.setAutoHide(true);

        // Add button to phone field container
        HBox phoneContainer = (HBox) phoneField.getParent();
        phoneContainer.getChildren().add(0, countrySelectBtn);

        // Show popup on button click
        countrySelectBtn.setOnAction(e -> {
            if (countryPopup.isShowing()) {
                countryPopup.hide();
            } else {
                countrySearchField.clear();
                countryListView.setItems(FXCollections.observableArrayList(countries));
                countryPopup.show(countrySelectBtn,
                        countrySelectBtn.localToScreen(countrySelectBtn.getBoundsInLocal()).getMinX(),
                        countrySelectBtn.localToScreen(countrySelectBtn.getBoundsInLocal()).getMaxY());
            }
        });
    }

    private void updateCountryButtonText() {
        countrySelectBtn.setText(selectedCountry.getFlag() + " " + selectedCountry.getCode());
    }

    @FXML
    private void handleAdd() {
        if (nomField.getText().trim().isEmpty() ||
                secteurField.getText().trim().isEmpty() ||
                matriculeField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Information",
                    "Please fill in all required fields (Nom, Secteur, Matricule Fiscale).");
            return;
        }

        String phoneNumber = phoneField.getText().trim();
        if (!phoneNumber.isEmpty() && isPhoneVerified) {
            // Phone is verified, proceed with adding
            phoneNumber = selectedCountry.getCode() + phoneNumber;
            Entreprise entreprise = new Entreprise(
                    nomField.getText().trim(),
                    secteurField.getText().trim(),
                    matriculeField.getText().trim(),
                    phoneNumber
            );
            entreprise.setPhoneVerified(true);

            try {
                entrepriseService.add(entreprise);
                clearFields();
                refreshList();
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Company " + entreprise.getNom() + " has been added successfully.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Failed to add company: " + e.getMessage());
            }
            return;
        } else if (!phoneNumber.isEmpty()) {
            // Show popup for unverified phone
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Phone Verification Required");
            alert.setHeaderText("Verify Phone Number");
            alert.setContentText("Please verify the phone number before adding the company.\nWould you like to verify it now?");

            ButtonType verifyButton = new ButtonType("Verify Now");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(verifyButton, cancelButton);

            alert.showAndWait().ifPresent(response -> {
                if (response == verifyButton) {
                    verifyPhone();
                }
            });
            return;
        }

        // No phone number case
        Entreprise entreprise = new Entreprise(
                nomField.getText().trim(),
                secteurField.getText().trim(),
                matriculeField.getText().trim(),
                null
        );

        try {
            entrepriseService.add(entreprise);
            clearFields();
            refreshList();
            showAlert(Alert.AlertType.INFORMATION, "Success",
                    "Company " + entreprise.getNom() + " has been added successfully.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to add company: " + e.getMessage());
        }
    }

    @FXML
    private void verifyPhone() {
        String phoneNumber = phoneField.getText();//bech tekho txt mawjoud fi phonefield w tsavih fl phone num

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a phone number.");
            return;
        }

        // Add country code to phone number
        phoneNumber = selectedCountry.getCode() + phoneNumber.trim();

        JSONObject result = smsVerificationService.sendVerificationCode(phoneNumber);
        if (result.getBoolean("success")) {//rani batht l code
            showAlert(Alert.AlertType.INFORMATION, "Code Sent",
                    "Verification code has been sent to " + result.getString("to"));
            verificationCodeField.setDisable(false);//ki yoslo code tthal
            submitVerificationBtn.setDisable(false);//ki thot code tthal
        } else {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to send verification code: " + result.getString("error"));
        }
    }

    @FXML
    private void submitVerificationCode() {
        String phoneNumber = phoneField.getText();//num w code verif
        String code = verificationCodeField.getText();

        if (code == null || code.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter the verification code.");
            return;
        }

        JSONObject result = smsVerificationService.verifyCode(phoneNumber, code);//bech iverifi code howa w le
        if (result.getBoolean("success") && result.getBoolean("valid")) {
            if (selectedEntreprise != null) {//nodekhlo nverif num mteeha
                selectedEntreprise.setPhoneVerified(true);
                entrepriseService.update(selectedEntreprise);
            }
            isPhoneVerified = true;//si verif pop up
            showAlert(Alert.AlertType.INFORMATION, "Success", "Phone number verified successfully!");
            verificationCodeField.setDisable(true);//nsakro
            submitVerificationBtn.setDisable(true);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid verification code. Please try again.");
        }
    }

    @FXML
    private void handleUpdate() {
        if (selectedEntreprise == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection",
                    "Please select a company to update.");
            return;
        }

        if (nomField.getText().trim().isEmpty() ||
                secteurField.getText().trim().isEmpty() ||
                matriculeField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Information",
                    "Please fill in all required fields (Nom, Secteur, Matricule Fiscale).");
            return;
        }

        String phoneNumber = phoneField.getText().trim();
        if (phoneNumber.isEmpty()) {
            phoneNumber = null;
        }

        // Check phone verification if changed
        if (phoneNumber != null && !phoneNumber.equals(selectedEntreprise.getPhoneNumber()) && !selectedEntreprise.isPhoneVerified()) {
            showAlert(Alert.AlertType.WARNING, "Unverified Phone",
                    "Please verify the phone number before updating the company.");
            return;
        }

        selectedEntreprise.setNom(nomField.getText().trim());
        selectedEntreprise.setSecteur(secteurField.getText().trim());
        selectedEntreprise.setMatriculeFiscale(matriculeField.getText().trim());
        selectedEntreprise.setPhoneNumber(phoneNumber);

        try {
            entrepriseService.update(selectedEntreprise);
            refreshList();
            showAlert(Alert.AlertType.INFORMATION, "Success",
                    "Company " + selectedEntreprise.getNom() + " has been updated successfully.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to update company: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        if (selectedEntreprise == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection",
                    "Please select a company to delete.");
            return;
        }

        try {
            entrepriseService.delete(selectedEntreprise.getId());
            clearFields();
            refreshList();
            showAlert(Alert.AlertType.INFORMATION, "Success",
                    "Company has been deleted successfully.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to delete company: " + e.getMessage());
        }
    }

    private void refreshList() {
        entrepriseItems.clear();
        entrepriseItems.addAll(entrepriseService.getAll());
    }

    private void clearFields() {
        nomField.clear();
        secteurField.clear();
        matriculeField.clear();
        phoneField.clear();
        verificationCodeField.clear();
        selectedEntreprise = null;
        isPhoneVerified = false;

        // Disable verification fields
        verificationCodeField.setDisable(true);
        submitVerificationBtn.setDisable(true);
    }

    private void populateFields(Entreprise entreprise) {
        nomField.setText(entreprise.getNom());
        secteurField.setText(entreprise.getSecteur());
        matriculeField.setText(entreprise.getMatriculeFiscale());

        // Handle phone number with country code
        String phoneNumber = entreprise.getPhoneNumber();
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            // Find matching country code
            selectedCountry = countries.stream()
                    .filter(c -> phoneNumber.startsWith(c.getCode()))
                    .findFirst()
                    .orElse(countries.get(0));
            updateCountryButtonText();
            phoneField.setText(phoneNumber.substring(selectedCountry.getCode().length()));
        } else {
            phoneField.setText("");
        }

        // Set verification fields state
        verificationCodeField.setDisable(!entreprise.isPhoneVerified());
        submitVerificationBtn.setDisable(!entreprise.isPhoneVerified());
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleExport() {
        try {
            //apachepoi
            Workbook workbook = new XSSFWorkbook();//fct poi yasnaa l workbook excel
            Sheet sheet = workbook.createSheet("Entreprises");

            // Create header style star lowel
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Create headers naabih
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Name", "Sector", "Fiscal ID", "Phone Number", "Phone Verified"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Fill data
            int rowNum = 1;//0headers
            for (Entreprise entreprise : entrepriseService.getAll()) {//tjib li andi fi bdd
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entreprise.getId());
                row.createCell(1).setCellValue(entreprise.getNom());
                row.createCell(2).setCellValue(entreprise.getSecteur());
                row.createCell(3).setCellValue(entreprise.getMatriculeFiscale());
                row.createCell(4).setCellValue(entreprise.getPhoneNumber() != null ? entreprise.getPhoneNumber() : "");
                row.createCell(5).setCellValue(entreprise.isPhoneVerified());
            }

            // Autosize columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Save file
            FileChooser fileChooser = new FileChooser();//nakhtar win bech nsavi
            fileChooser.setTitle("Save Excel File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
            );
            fileChooser.setInitialFileName("entreprises.xlsx");

            File file = fileChooser.showSaveDialog(entrepriseList.getScene().getWindow());
            if (file != null) {
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Enterprises have been exported to Excel successfully!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to export to Excel: " + e.getMessage());
        }
    }
} 