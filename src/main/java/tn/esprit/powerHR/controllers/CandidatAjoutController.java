package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import tn.esprit.powerHR.models.Candidat;
import tn.esprit.powerHR.models.EntrepriseDep.*;
import tn.esprit.powerHR.services.ServiceCandidat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;

public class CandidatAjoutController {

    @FXML
    private TextField tf_nom;
    @FXML
    private TextField tf_prenom;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_Num;
    @FXML
    private Button bt_upload;
    @FXML
    private Button bt_ajouterCandidat;
    @FXML
    private ListView<Candidat> lv_ajout;

    private Blob cvPdfBlob;
    private String cvFileName;

    private ServiceCandidat sc = new ServiceCandidat();

    @FXML
    private void initialize() {
        refreshList();

        bt_upload.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner un CV (PDF)");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File selectedFile = fileChooser.showOpenDialog(bt_upload.getScene().getWindow());

            if (selectedFile != null) {
                try {
                    Path path = Paths.get(selectedFile.getAbsolutePath());
                    byte[] pdfBytes = Files.readAllBytes(path);

                    // Vérifier la taille du fichier avant de stocker (16MB max pour MEDIUMBLOB, 4GB max pour LONGBLOB)
                    if (pdfBytes.length > 16000000) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Le fichier est trop volumineux (max 16MB).");
                        return;
                    }

                    cvPdfBlob = new SerialBlob(pdfBytes);
                    cvFileName = selectedFile.getName();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "CV sélectionné : " + cvFileName);

                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la lecture du fichier CV.");
                }
            }
        });
    }

    @FXML
    private void refreshList() {
        try {
            List<Candidat> candidats = sc.getAll();
            ObservableList<Candidat> observableList = FXCollections.observableArrayList(candidats);
            lv_ajout.setItems(observableList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des candidats : " + e.getMessage());
        }
    }

    @FXML
    private void AjouterCandidat(ActionEvent event) {
        if (tf_nom.getText().isEmpty() || tf_prenom.getText().isEmpty() || tf_email.getText().isEmpty() ||
                tf_Num.getText().isEmpty() || cvPdfBlob == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs et le CV sont obligatoires.");
            return;
        }

        if (!isValidEmail(tf_email.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer un email valide.");
            return;
        }

        if (!isValidPhoneNumber(tf_Num.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le numéro doit contenir exactement 8 chiffres.");
            return;
        }

        String nom = tf_nom.getText();
        String prenom = tf_prenom.getText();
        String email = tf_email.getText();
        String telephone = tf_Num.getText();

        Candidat candidat = new Candidat();
        candidat.setNom(nom);
        candidat.setPrenom(prenom);
        candidat.setEmail(email);
        candidat.setTelephone(telephone);
        candidat.setCvPdf(cvPdfBlob);

        Entreprise entreprise = new Entreprise(1, "Entreprise XYZ", "Adresse", "Secteur", null, false);
        candidat.setEntreprise(entreprise);

        try {
            sc.add(candidat);
            clearFields();
            refreshList();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Candidat ajouté avec succès !");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite : " + e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isValidPhoneNumber(String number) {
        return number.matches("\\d{8}");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        tf_nom.clear();
        tf_prenom.clear();
        tf_email.clear();
        tf_Num.clear();
        cvPdfBlob = null;
        cvFileName = null;
    }
}
