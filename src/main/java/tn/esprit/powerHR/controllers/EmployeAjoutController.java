package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.Departement;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.services.ServiceEmploye;

import java.util.List;

public class EmployeAjoutController {

    @FXML
    private Button bt_ajouteremploye;

    @FXML
    private Button bt_modiferemploye;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_suppemploye;

    @FXML
    private ChoiceBox<Poste> cb_poste;

    @FXML
    private ListView<Employe> lv_Ajout;

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
    private void initialize() {
        cb_poste.getItems().addAll(Poste.values());
        refreshList();
    }

    @FXML
    private void refreshList() {
        try {
            List<Employe> list = se.getAll();
            ObservableList<Employe> observableList = FXCollections.observableList(list);
            lv_Ajout.setItems(observableList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des employés : " + e.getMessage());
        }
    }

    @FXML
    void AjouterEmploye(ActionEvent event) {
        if (tf_username.getText().isEmpty() || tf_pwd.getText().isEmpty() || cb_poste.getValue() == null ||
                tf_salaire.getText().isEmpty() || tf_rib.getText().isEmpty() || tf_CodeSociale.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Tous les champs sont obligatoires.");
            return;
        }

        if (!isValidSalaire(tf_salaire.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le salaire doit être un nombre valide (ex: 1500.50).");
            return;
        }

        if (!isValidRIB(tf_rib.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le RIB doit contenir exactement 12 chiffres.");
            return;
        }

        if (!isValidCodeSociale(tf_CodeSociale.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le Code Sociale doit contenir exactement 4 chiffres.");
            return;
        }

        String username = tf_username.getText();
        String password = tf_pwd.getText();
        String poste = cb_poste.getValue().name();
        double salaire = Double.parseDouble(tf_salaire.getText());
        String rib = tf_rib.getText();
        String codeSociale = tf_CodeSociale.getText();


        Employe employe = new Employe();
        employe.setUsername(username);
        employe.setPassword(password);
        employe.setPoste(poste);
        employe.setSalaire(salaire);
        employe.setRib(rib);
        employe.setCodeSociale(codeSociale);

        Departement departement = new Departement(1,"math","ababba",1);
        employe.setDepartement(departement);
        try {
            se.add(employe);
            clearFields();
            refreshList();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout de l'employé : " + e.getMessage());
        }
    }

    private boolean isValidSalaire(String salaire) {
        try {
            Double.parseDouble(salaire);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidRIB(String rib) {
        return rib.matches("\\d{12}");
    }

    private boolean isValidCodeSociale(String codeSociale) {
        return codeSociale.matches("\\d{4}");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        tf_username.clear();
        tf_pwd.clear();
        tf_salaire.clear();
        tf_rib.clear();
        tf_CodeSociale.clear();
        cb_poste.setValue(null);
    }
}
