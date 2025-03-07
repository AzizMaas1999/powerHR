package tn.esprit.powerHR.controllers.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.Departement;
import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.services.DepartementService;
import tn.esprit.powerHR.services.User.ServiceEmploye;

import java.io.IOException;
import java.util.List;

public class EmployeAjoutController {
    @FXML
    private AnchorPane MainPane;

    @FXML
    private ImageView backIcon;

    @FXML
    private ComboBox<Poste> cb_poste;

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
    @FXML
    private ComboBox<String> cb_NomDepartement;

    ServiceEmploye se = new ServiceEmploye();
    DepartementService sd = new DepartementService();
    @FXML
    private void initialize() {
        cb_poste.setItems(FXCollections.observableArrayList(Poste.values()));
        loadDepartements();
        handleBackButton();
    }
    @FXML
    private void handleBackButton() {
        backIcon.setOnMouseClicked(event -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ManageEmploye.fxml"));
                Parent previousPage = loader.load();

                Stage stage = (Stage) MainPane.getScene().getWindow();
                Scene scene = new Scene(previousPage);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void loadDepartements() {
        List<String> departements = sd.getAll().stream()
                .map(Departement::getNom)
                .toList();
        ObservableList<String> departementList = FXCollections.observableArrayList(departements);
        cb_NomDepartement.setItems(departementList);
    }

    @FXML
    void AjouterEmploye(ActionEvent event) {
        if (tf_username.getText().isEmpty() || tf_pwd.getText().isEmpty() || cb_poste.getValue() == null ||
                tf_salaire.getText().isEmpty() || tf_rib.getText().isEmpty() || tf_CodeSociale.getText().isEmpty() || cb_NomDepartement.getValue()==null){
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Tous les champs sont obligatoires.");
            return;
        }

        if (!isValidSalaire(tf_salaire.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le salaire doit être un nombre");
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
        Poste poste = cb_poste.getValue();
        double salaire = Double.parseDouble(tf_salaire.getText());
        String rib = tf_rib.getText();
        String codeSociale = tf_CodeSociale.getText();
        String nomDepartement = cb_NomDepartement.getValue();

        Employe employe = new Employe();
        employe.setUsername(username);
        employe.setPassword(password);
        employe.setPoste(poste);
        employe.setSalaire(salaire);
        employe.setRib(rib);
        employe.setCodeSociale(codeSociale);
        employe.setDepartement(nomDepartement);

        try {
            se.add(employe);
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Employé ajouté avec succès !");
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
        cb_NomDepartement.setValue(null);
        cb_poste.setValue(null);

    }
}
