package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.services.ServiceEmploye;
import tn.esprit.powerHR.controllers.enums.Poste;
import java.util.List;

public class EmployeModifController {

    @FXML
    private Button bt_submit;

    @FXML
    private ComboBox<Poste> cb_poste;

    @FXML
    private ChoiceBox<String> cb_nomDepartement;

    @FXML
    private TextField tf_CodeSociale;

    @FXML
    private TextField tf_pwd;

    @FXML
    private TextField tf_rib;

    @FXML
    private TextField tf_salaire;

    @FXML
    private TextField tf_username;

    ServiceEmploye se = new ServiceEmploye();

    @FXML
    private Employe selectedEmploye;

    public void setSelectedEmploye(Employe e) {
        this.selectedEmploye = e;
        populateFields();
    }

    private void populateFields() {
        if (selectedEmploye != null) {
            tf_username.setText(selectedEmploye.getUsername());
            tf_pwd.setText(selectedEmploye.getPassword());
            tf_CodeSociale.setText(selectedEmploye.getCodeSociale());
            tf_rib.setText(selectedEmploye.getRib());
            tf_salaire.setText(String.valueOf(selectedEmploye.getSalaire()));
            cb_poste.setValue(selectedEmploye.getPoste());
            cb_nomDepartement.setValue(selectedEmploye.getDepartement());
        }
    }

    @FXML
    void initialize() {
        cb_poste.setItems(FXCollections.observableArrayList(Poste.values()));

        List<String> departements = se.getAllDepartements();
        cb_nomDepartement.getItems().setAll(departements);
    }

    @FXML
    void ModifEmploye(ActionEvent event) {
        String username = tf_username.getText();
        String password = tf_pwd.getText();
        String codeSociale = tf_CodeSociale.getText();
        String rib = tf_rib.getText();
        String salaireText = tf_salaire.getText();

        if (username.isEmpty() || password.isEmpty() || codeSociale.isEmpty() || rib.isEmpty() || salaireText.isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Tous les champs sont obligatoires.");
            return;
        }

        double salaire;
        try {
            salaire = Double.parseDouble(salaireText);
        } catch (NumberFormatException ex) {
            showAlert(AlertType.ERROR, "Erreur", "Le salaire doit être un nombre valide.");
            return;
        }

        Poste selectedPoste = cb_poste.getValue();
        if (selectedPoste == null) {
            showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un poste.");
            return;
        }

        String selectedDepartement = cb_nomDepartement.getValue();
        if (selectedDepartement == null) {
            showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un département.");
            return;
        }

        selectedEmploye.setUsername(username);
        selectedEmploye.setPassword(password);
        selectedEmploye.setCodeSociale(codeSociale);
        selectedEmploye.setSalaire(salaire);
        selectedEmploye.setRib(rib);
        selectedEmploye.setPoste(selectedPoste);
        selectedEmploye.setDepartement(selectedDepartement);

        try {
            se.update(selectedEmploye);
            showAlert(AlertType.INFORMATION, "Succès", "Employé modifié avec succès.");
        } catch (Exception exception) {
            showAlert(AlertType.ERROR, "Erreur", "Erreur lors de la modification de l'employé: " + exception.getMessage());
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
