package tn.esprit.powerHr.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import tn.esprit.powerHr.models.Departement;
import tn.esprit.powerHr.models.Entreprise;
import tn.esprit.powerHr.services.DepartementService;
import tn.esprit.powerHr.services.EntrepriseService;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartementController implements Initializable {
    @FXML private TextField searchField;
    @FXML private TextField nomField;
    @FXML private TextField descriptionField;
    @FXML private ComboBox<Entreprise> entrepriseComboBox;
    
    @FXML private ListView<Departement> departementList;

    private DepartementService departementService;
    private EntrepriseService entrepriseService;
    private Departement selectedDepartement;
    private ObservableList<Departement> departementItems;
    private FilteredList<Departement> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        departementService = new DepartementService();
        entrepriseService = new EntrepriseService();
        
        // Initialize Observable List and Filtered List
        departementItems = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(departementItems, p -> true);

        // Setup ListView cell factory
        departementList.setCellFactory(lv -> new ListCell<Departement>() {
            @Override
            protected void updateItem(Departement item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox container = new VBox(5);
                    container.setPadding(new Insets(5, 0, 5, 0));
                    
                    Label nameLabel = new Label(item.getNom());
                    nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                    
                    Label descLabel = new Label(item.getDescription());
                    
                    // Get entreprise name
                    Entreprise entreprise = entrepriseService.getById(item.getEntrepriseId());
                    Label entrepriseLabel = new Label("Entreprise: " + 
                        (entreprise != null ? entreprise.getNom() : "N/A"));
                    
                    container.getChildren().addAll(nameLabel, descLabel, entrepriseLabel);
                    setGraphic(container);
                }
            }
        });

        // Bind the ListView to the Filtered List
        departementList.setItems(filteredList);

        // Add search field listener
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(departement -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (departement.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (departement.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        // Add selection listener
        departementList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedDepartement = newSelection;
                populateFields(newSelection);
            }
        });

        // Setup entreprise ComboBox
        setupEntrepriseComboBox();

        refreshList();
    }

    private void setupEntrepriseComboBox() {
        // Load entreprises
        List<Entreprise> entreprises = entrepriseService.getAll();
        entrepriseComboBox.setItems(FXCollections.observableArrayList(entreprises));
        
        // Set the display format
        entrepriseComboBox.setConverter(new StringConverter<Entreprise>() {
            @Override
            public String toString(Entreprise entreprise) {
                return entreprise != null ? entreprise.getNom() : "";
            }

            @Override
            public Entreprise fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    private void handleAdd() {
        if (entrepriseComboBox.getValue() == null) {
            showAlert("Please select an entreprise");
            return;
        }

        Departement departement = new Departement(
            nomField.getText(),
            descriptionField.getText(),
            entrepriseComboBox.getValue().getId()
        );
        
        departementService.add(departement);
        clearFields();
        refreshList();
    }

    @FXML
    private void handleUpdate() {
        if (selectedDepartement == null) {
            showAlert("Please select a departement to update");
            return;
        }

        selectedDepartement.setNom(nomField.getText());
        selectedDepartement.setDescription(descriptionField.getText());
        selectedDepartement.setEntrepriseId(entrepriseComboBox.getValue().getId());

        departementService.update(selectedDepartement);
        clearFields();
        refreshList();
    }

    @FXML
    private void handleDelete() {
        if (selectedDepartement == null) {
            showAlert("Please select a departement to delete");
            return;
        }

        departementService.delete(selectedDepartement.getId());
        clearFields();
        refreshList();
    }

    private void refreshList() {
        departementItems.clear();
        departementItems.addAll(departementService.getAll());
    }

    private void clearFields() {
        nomField.clear();
        descriptionField.clear();
        entrepriseComboBox.setValue(null);
        selectedDepartement = null;
    }

    private void populateFields(Departement departement) {
        nomField.setText(departement.getNom());
        descriptionField.setText(departement.getDescription());
        // Find and select the corresponding entreprise
        entrepriseComboBox.getItems().stream()
            .filter(e -> e.getId() == departement.getEntrepriseId())
            .findFirst()
            .ifPresent(entrepriseComboBox::setValue);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 