package tn.esprit.powerHr.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import tn.esprit.powerHr.entities.Entreprise;
import tn.esprit.powerHr.services.EntrepriseService;

import java.net.URL;
import java.util.ResourceBundle;

public class EntrepriseController implements Initializable {
    @FXML private TextField searchField;
    @FXML private TextField nomField;
    @FXML private TextField secteurField;
    @FXML private TextField matriculeField;
    
    @FXML private ListView<Entreprise> entrepriseList;

    private EntrepriseService entrepriseService;
    private Entreprise selectedEntreprise;

    private ObservableList<Entreprise> entrepriseItems;
    private FilteredList<Entreprise> filteredList;

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
                    
                    container.getChildren().addAll(nameLabel, sectorLabel, matriculeLabel);
                    setGraphic(container);
                }
            }
        });

        // Bind the ListView to the Filtered List
        entrepriseList.setItems(filteredList);

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
        Entreprise entreprise = new Entreprise(
            nomField.getText(),
            secteurField.getText(),
            matriculeField.getText()
        );
        
        entrepriseService.add(entreprise);
        clearFields();
        refreshList();
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

    private void refreshList() {
        entrepriseItems.clear();
        entrepriseItems.addAll(entrepriseService.getAll());
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