package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.geometry.Insets;
import tn.esprit.powerHR.models.Entreprise;
import tn.esprit.powerHR.services.EntrepriseService;
import tn.esprit.powerHR.services.EmailService;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class EntrepriseController implements Initializable {
    @FXML private TextField searchField;
    @FXML private TextField nomField;
    @FXML private TextField secteurField;
    @FXML private TextField matriculeField;
    @FXML private TextField emailField;
    @FXML private Button validateEmailBtn;
    
    @FXML private ListView<Entreprise> entrepriseList;

    private EntrepriseService entrepriseService;
    private Entreprise selectedEntreprise;

    private ObservableList<Entreprise> entrepriseItems;
    private FilteredList<Entreprise> filteredList;

    private final EmailService emailService = new EmailService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        entrepriseService = new EntrepriseService();
        
        // Initialize Observable List and Filtered List
        entrepriseItems = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(entrepriseItems, p -> true);

        // Setup ListView cell factory
        entrepriseList.setCellFactory(lv -> new ListCell<Entreprise>() {
            @Override
            protected void updateItem(Entreprise item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox container = new VBox(5);
                    container.setPadding(new Insets(5, 0, 5, 0));
                    
                    Label nameLabel = new Label(item.getNom());
                    nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                    
                    Label sectorLabel = new Label("Secteur: " + item.getSecteur());
                    Label matriculeLabel = new Label("Matricule: " + item.getMatriculeFiscale());
                    Label emailLabel = new Label("Email: " + item.getEmail());
                    
                    container.getChildren().addAll(nameLabel, sectorLabel, matriculeLabel, emailLabel);
                    setGraphic(container);
                }
            }
        });

        entrepriseList.setItems(filteredList);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(entreprise -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (entreprise.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (entreprise.getSecteur().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (entreprise.getMatriculeFiscale().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (entreprise.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        // Add selection listener
        entrepriseList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedEntreprise = newSelection;
                populateFields(newSelection);
            }
        });

        refreshList();
    }

    @FXML
    private void handleAdd() {
        // First check if required fields are filled
        if (nomField.getText().trim().isEmpty() || 
            secteurField.getText().trim().isEmpty() || 
            matriculeField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Information", 
                "Please fill in all required fields (Nom, Secteur, Matricule Fiscale).");
            return;
        }

        String email = emailField.getText().trim();
        
        // If email is provided, validate it first
        if (!email.isEmpty()) {
            System.out.println("Validating email before adding: " + email);
            JSONObject validationResult = emailService.validateEmail(email);
            
            if (validationResult == null || !validationResult.getBoolean("is_valid")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Email", 
                    "The provided email address is not valid. Please check and try again or leave it empty.");
                return;
            }
        }
        
        // Create entreprise object
        Entreprise entreprise = new Entreprise(
            nomField.getText().trim(),
            secteurField.getText().trim(),
            matriculeField.getText().trim(),
            email.isEmpty() ? null : email
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
    private void handleUpdate() {
        if (selectedEntreprise == null) {
            showAlert("Please select an entreprise to update");
            return;
        }

        selectedEntreprise.setNom(nomField.getText());
        selectedEntreprise.setSecteur(secteurField.getText());
        selectedEntreprise.setMatriculeFiscale(matriculeField.getText());

        entrepriseService.update(selectedEntreprise);
        clearFields();
        refreshList();
    }

    @FXML
    private void handleDelete() {
        if (selectedEntreprise == null) {
            showAlert("Please select an entreprise to delete");
            return;
        }

        entrepriseService.delete(selectedEntreprise.getId());
        clearFields();
        refreshList();
    }

    @FXML
    private void validateEmail() {
        String email = emailField.getText();
        System.out.println("Validate button clicked for email: " + email);
        
        if (email == null || email.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter an email address.");
            return;
        }

        System.out.println("Calling email service...");
        JSONObject validationResult = emailService.validateEmail(email);
        System.out.println("Validation result: " + validationResult);

        if (validationResult != null) {
            try {
                System.out.println("Creating dialog...");
                EmailValidationDialog dialog = new EmailValidationDialog(validationResult);
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.initOwner(emailField.getScene().getWindow());
                dialog.showAndWait();
            } catch (Exception e) {
                System.err.println("Error showing dialog: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Validation Error", 
                "Failed to validate email. Please try again.");
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
        emailField.clear();
        selectedEntreprise = null;
    }

    private void populateFields(Entreprise entreprise) {
        nomField.setText(entreprise.getNom());
        secteurField.setText(entreprise.getSecteur());
        matriculeField.setText(entreprise.getMatriculeFiscale());
        emailField.setText(entreprise.getEmail());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 