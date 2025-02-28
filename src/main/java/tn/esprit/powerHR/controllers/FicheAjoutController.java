package tn.esprit.powerHR.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.FicheEmploye;
import tn.esprit.powerHR.services.ServiceFicheEmploye;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.regex.Pattern;

public class FicheAjoutController {

    @FXML
    private TextField tf_cin, tf_email, tf_nom, tf_prenom, tf_rib, tf_adresse, tf_city, tf_zip, tf_numTel;

    @FXML
    private Button bt_submit, bt_upload;

    private Blob selectedFile;
    private String cvFileName;

    private final ServiceFicheEmploye sf = new ServiceFicheEmploye();

    @FXML
    private void AjouterFiche() {
        if (!validateFields()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", "Veuillez vérifier les champs saisis.");
            return;
        }

        String nom = tf_nom.getText();
        String prenom = tf_prenom.getText();
        String adresse = tf_adresse.getText();
        String zip = tf_zip.getText();
        String cin = tf_cin.getText();
        String email = tf_email.getText();
        String num = tf_numTel.getText();
        String city = tf_city.getText();

        FicheEmploye fe = new FicheEmploye();
        fe.setNom(nom);
        fe.setPrenom(prenom);
        fe.setAdresse(adresse);
        fe.setZip(zip);
        fe.setCin(cin);
        fe.setEmail(email);
        fe.setNumTel(num);
        fe.setCity(city);
        fe.setversionPdf(selectedFile);

        try {
            sf.add(fe);
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Fiche ajoutée avec succès !");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite : " + e.getMessage());
        }
    }

    @FXML
    private void handleFileUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un CV (PDF)");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showOpenDialog(bt_upload.getScene().getWindow());

        if (file != null) {
            try {
                Path path = Paths.get(file.getAbsolutePath());
                byte[] pdfBytes = Files.readAllBytes(path);

                selectedFile = new SerialBlob(pdfBytes);
                cvFileName = file.getName();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "CV sélectionné : " + cvFileName);

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la lecture du fichier CV.");
            }
        }
    }

    private boolean validateFields() {
        if (tf_nom.getText().isEmpty() || tf_prenom.getText().isEmpty() || tf_cin.getText().isEmpty()
                || tf_email.getText().isEmpty() || tf_rib.getText().isEmpty() || tf_numTel.getText().isEmpty()
                || tf_adresse.getText().isEmpty() || tf_city.getText().isEmpty() || tf_zip.getText().isEmpty()) {
            return false;
        }

        if (!Pattern.matches("\\d{8}", tf_cin.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le CIN doit contenir 8 chiffres.");
            return false;
        }

        if (!Pattern.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", tf_email.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Email invalide.");
            return false;
        }

        if (!Pattern.matches("\\d{8}", tf_numTel.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le numéro de téléphone doit contenir 8 chiffres.");
            return false;
        }

        if (!Pattern.matches("\\d{12}", tf_rib.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le RIB doit contenir 12 chiffres.");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        tf_nom.clear();
        tf_prenom.clear();
        tf_cin.clear();
        tf_email.clear();
        tf_rib.clear();
        tf_numTel.clear();
        tf_adresse.clear();
        tf_city.clear();
        tf_zip.clear();
        selectedFile = null;
        cvFileName = null;
    }
}
