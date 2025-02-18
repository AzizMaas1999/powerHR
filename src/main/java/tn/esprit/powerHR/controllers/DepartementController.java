package tn.esprit.powerHr.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.powerHr.entities.Departement;
import tn.esprit.powerHr.entities.Entreprise;
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
    
    @FXML private TableView<Departement> departementTable;
    @FXML private TableColumn<Departement, Integer> idColumn;
    @FXML private TableColumn<Departement, String> nomColumn;
    @FXML private TableColumn<Departement, String> descriptionColumn;

    private DepartementService departementService;
    private EntrepriseService entrepriseService;
    private Departement selectedDepartement;
    private ObservableList<Departement> departementList;
    private FilteredList<Departement> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        departementService = new DepartementService();
        entrepriseService = new EntrepriseService();
        
        // Initialize table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Initialize Observable List and Filtered List
        departementList = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(departementList, p -> true);

        // Bind the TableView to the Filtered List
        departementTable.setItems(filteredList);

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
        departementTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedDepartement = newSelection;
                populateFields(newSelection);
            }
        });

        // Setup entreprise ComboBox
        setupEntrepriseComboBox();

        refreshTable();
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
        refreshTable();
    }

    @FXML
    private void handleUpdate() {
        if (selectedDepartement == null) {
            showAlert("Please select a departement to update");
            return;
        }

        selectedDepartement.setNom(nomField.getText());
        selectedDepartement.setDescription(descriptionField.getText());

        departementService.update(selectedDepartement);
        clearFields();
        refreshTable();
    }

    @FXML
    private void handleDelete() {
        if (selectedDepartement == null) {
            showAlert("Please select a departement to delete");
            return;
        }

        departementService.delete(selectedDepartement.getId());
        clearFields();
        refreshTable();
    }

    private void refreshTable() {
        departementList.clear();
        departementList.addAll(departementService.getAll());
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