package tn.esprit.powerHr.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.powerHr.entities.Entreprise;
import tn.esprit.powerHr.services.EntrepriseService;

import java.net.URL;
import java.util.ResourceBundle;

public class EntrepriseController implements Initializable {
    @FXML private TextField searchField;
    @FXML private TextField nomField;
    @FXML private TextField secteurField;
    @FXML private TextField matriculeField;
    
    @FXML private TableView<Entreprise> entrepriseTable;
    @FXML private TableColumn<Entreprise, Integer> idColumn;
    @FXML private TableColumn<Entreprise, String> nomColumn;
    @FXML private TableColumn<Entreprise, String> secteurColumn;
    @FXML private TableColumn<Entreprise, String> matriculeColumn;

    private EntrepriseService entrepriseService;
    private Entreprise selectedEntreprise;

    private ObservableList<Entreprise> entrepriseList;
    private FilteredList<Entreprise> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        entrepriseService = new EntrepriseService();
        
        // Initialize table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        secteurColumn.setCellValueFactory(new PropertyValueFactory<>("secteur"));
        matriculeColumn.setCellValueFactory(new PropertyValueFactory<>("matriculeFiscale"));

        // Initialize Observable List and Filtered List
        entrepriseList = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(entrepriseList, p -> true);

        // Bind the TableView to the Filtered List
        entrepriseTable.setItems(filteredList);

        // Add search field listener
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
                return false;
            });
        });

        // Add selection listener
        entrepriseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedEntreprise = newSelection;
                populateFields(newSelection);
            }
        });

        refreshTable();
    }

    @FXML
    private void handleAdd() {
        Entreprise entreprise = new Entreprise(
            nomField.getText(),
            secteurField.getText(),
            matriculeField.getText()
        );
        
        entrepriseService.add(entreprise);
        clearFields();
        refreshTable();
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
        refreshTable();
    }

    @FXML
    private void handleDelete() {
        if (selectedEntreprise == null) {
            showAlert("Please select an entreprise to delete");
            return;
        }

        entrepriseService.delete(selectedEntreprise.getId());
        clearFields();
        refreshTable();
    }

    private void refreshTable() {
        entrepriseList.clear();
        entrepriseList.addAll(entrepriseService.getAll());
    }

    private void clearFields() {
        nomField.clear();
        secteurField.clear();
        matriculeField.clear();
        selectedEntreprise = null;
    }

    private void populateFields(Entreprise entreprise) {
        nomField.setText(entreprise.getNom());
        secteurField.setText(entreprise.getSecteur());
        matriculeField.setText(entreprise.getMatriculeFiscale());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 