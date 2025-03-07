package tn.esprit.powerHR.controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.User.FicheEmploye;
import tn.esprit.powerHR.services.User.ServiceFicheEmploye;

import java.io.IOException;

public class FicheModifController {

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_upload;

    @FXML
    private TextField tf_adresse;

    @FXML
    private TextField tf_cin;

    @FXML
    private TextField tf_city;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_nom;

    @FXML
    private TextField tf_numTel;

    @FXML
    private TextField tf_prenom;

    @FXML
    private TextField tf_zip;
    
        @FXML
        private AnchorPane MainPane;

        @FXML
        private ImageView backIcon;
        
        ServiceFicheEmploye sf = new ServiceFicheEmploye();

        @FXML
        private FicheEmploye selectedFiche;

        @FXML
        void initialize() {
            if (selectedFiche != null) {
                populateFields();
            }
            handleBackButton();
        }

        @FXML
        private void handleBackButton() {
            backIcon.setOnMouseClicked(event -> {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ManageFiche.fxml"));
                    Parent previousPage = loader.load();

                    Stage stage = (Stage) MainPane.getScene().getWindow();
                    Scene scene = new Scene(previousPage);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


        public void setSelectedFiche(FicheEmploye fe) {
            this.selectedFiche = fe;
            if (selectedFiche != null) {
                populateFields();
            }
        }

        private void populateFields() {
            if (selectedFiche != null) {
                tf_adresse.setText(selectedFiche.getAdresse());
                tf_cin.setText(selectedFiche.getCin());
                tf_city.setText(selectedFiche.getCity());
                tf_zip.setText(String.valueOf(selectedFiche.getZip()));
                tf_email.setText(selectedFiche.getEmail());
                tf_nom.setText(selectedFiche.getNom());
                tf_prenom.setText(selectedFiche.getPrenom());
                tf_numTel.setText(selectedFiche.getNumTel());
            }
        }

        @FXML
        void ModifFiche(ActionEvent event) {
            String nom = tf_nom.getText();
            String prenom = tf_prenom.getText();
            String adresse = tf_adresse.getText();
            String email = tf_email.getText();
            String cin = tf_cin.getText();
            String city = tf_city.getText();
            String numTel = tf_numTel.getText();
            String zip = tf_zip.getText();

            if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || email.isEmpty() || city.isEmpty() || numTel.isEmpty() || zip.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs sont obligatoires.");
                return;
            }

            selectedFiche.setNom(nom);
            selectedFiche.setPrenom(prenom);
            selectedFiche.setAdresse(adresse);
            selectedFiche.setEmail(email);
            selectedFiche.setCin(cin);
            selectedFiche.setNumTel(numTel);
            selectedFiche.setCity(city);
            selectedFiche.setZip(zip);

            try {
                sf.update(selectedFiche);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Employé modifié avec succès.");
            } catch (Exception exception) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification de l'employé: " + exception.getMessage());
            }
        }


        private void showAlert(Alert.AlertType alertType, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
}