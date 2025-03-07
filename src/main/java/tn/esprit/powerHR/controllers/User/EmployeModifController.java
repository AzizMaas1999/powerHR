package tn.esprit.powerHR.controllers.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.EntrepriseDep.Departement;
import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.services.EntrepriseDep.DepartementService;
import tn.esprit.powerHR.services.User.ServiceEmploye;
import tn.esprit.powerHR.controllers.enums.Poste;

import java.io.IOException;
import java.util.List;

public class EmployeModifController {
    @FXML
    private AnchorPane MainPane;

    @FXML
    private ImageView backIcon;

    @FXML
    private Button bt_submit;

    @FXML
    private ComboBox<Poste> cb_poste;

    @FXML
    private ComboBox<String> cb_NomDepartement;

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
    DepartementService sd = new DepartementService();

    @FXML
    private Employe selectedEmploye;

    @FXML
    void initialize() {
        cb_poste.setItems(FXCollections.observableArrayList(Poste.values()));
        loadDepartements();
        if (selectedEmploye != null) {
            populateFields();
        }
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

    public void setSelectedEmploye(Employe e) {
        this.selectedEmploye = e;
        if (selectedEmploye != null) {
            populateFields();
        }
    }

    private void populateFields() {
        if (selectedEmploye != null) {
            tf_username.setText(selectedEmploye.getUsername());
            tf_pwd.setText(selectedEmploye.getPassword());
            tf_CodeSociale.setText(selectedEmploye.getCodeSociale());
            tf_rib.setText(selectedEmploye.getRib());
            tf_salaire.setText(String.valueOf(selectedEmploye.getSalaire()));
            cb_poste.setValue(selectedEmploye.getPoste());
            cb_NomDepartement.setValue(selectedEmploye.getDepartement());
        }
    }

    @FXML
    void ModifEmploye(ActionEvent event) {
        String username = tf_username.getText();
        String password = tf_pwd.getText();
        String codeSociale = tf_CodeSociale.getText();
        String rib = tf_rib.getText();
        String salaireText = tf_salaire.getText();
        String nomDepartement = cb_NomDepartement.getValue();

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

        String selectedDepartementName = cb_NomDepartement.getValue();
        if (selectedDepartementName == null) {
            showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un département.");
            return;
        }

        selectedEmploye.setUsername(username);
        selectedEmploye.setPassword(password);
        selectedEmploye.setCodeSociale(codeSociale);
        selectedEmploye.setSalaire(salaire);
        selectedEmploye.setRib(rib);
        selectedEmploye.setPoste(selectedPoste);
        selectedEmploye.setDepartement(nomDepartement);

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
