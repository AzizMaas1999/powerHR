package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.FicheEmploye;
import tn.esprit.powerHR.services.ServiceFicheEmploye;


import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.List;

public class FicheAjoutController {

    @FXML
    private Button bt_ajouterfiche, bt_modiferfiche, bt_submit, bt_suppfiche;

    @FXML
    private TextField tf_Num, tf_adresse, tf_cin, tf_city, tf_email, tf_nom, tf_prenom, tf_rib, tf_zip;

    @FXML
    private Button bt_upload;

    @FXML
    private ListView<FicheEmploye> lv_fiches;

    private Blob pdfFileBlob;
    private String pdfFileName;

    private ServiceFicheEmploye sf = new ServiceFicheEmploye();

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

                    if (pdfBytes.length > 16000000) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Le fichier est trop volumineux (max 16MB).");
                        return;
                    }

                    pdfFileBlob = new SerialBlob(pdfBytes);
                    pdfFileName= selectedFile.getName();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "CV sélectionné : " + pdfFileName);

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
            List<FicheEmploye> list = sf.getAll();
            ObservableList<FicheEmploye> observableList = FXCollections.observableArrayList(list);
            lv_fiches.setItems(observableList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des fiches : " + e.getMessage());
        }
    }

    @FXML
    private void AjouterFiche(ActionEvent event) {
        if (tf_cin.getText().isEmpty() || tf_nom.getText().isEmpty() || tf_prenom.getText().isEmpty() || tf_email.getText().isEmpty()
                || tf_adresse.getText().isEmpty() || tf_city.getText().isEmpty() || tf_zip.getText().isEmpty() || tf_Num.getText().isEmpty() || pdfFileBlob == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Tous les champs sont obligatoires.");
            return;
        }
        if (!isValidPhoneNumber(tf_Num.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le Num doit contenir exactement 8 chiffres.");
            return;
        }

        if (!isValidCIN(tf_cin.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le Cin doit contenir exactement 8 chiffres.");
            return;
        }
        if (!isValidZIP(tf_zip.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le Zip doit contenir exactement 4 chiffres.");
            return;
        }
        String nom = tf_nom.getText();
        String prenom = tf_prenom.getText();
        String  adresse =tf_adresse.getText();
        String zip = tf_zip.getText();
        String cin = tf_cin.getText();
        String email = tf_email.getText();
        String num = tf_Num.getText();
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
        fe.setPdfFile(pdfFileBlob);

        Employe employe = new Employe(1,"fdkbgkndfg","fdkbgkndfg","chargesRH",445.2,"123456789125","fdkbgkndfg",null,null,null,null,null);
        fe.setEmploye(employe);

        try {
            sf.add(fe);
            clearFields();
            refreshList();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Fiche ajoutée avec succès !");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite : " + e.getMessage());
        }
    }

    private boolean validateFields() {
        if (tf_nom.getText().isEmpty() || tf_prenom.getText().isEmpty() || tf_email.getText().isEmpty() ||
                tf_Num.getText().isEmpty() || tf_cin.getText().isEmpty() || tf_adresse.getText().isEmpty() ||
                tf_city.getText().isEmpty() || tf_zip.getText().isEmpty() || tf_rib.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs sont obligatoires.");
            return false;
        }

        if (!isValidEmail(tf_email.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer un email valide.");
            return false;
        }

        if (!isValidPhoneNumber(tf_Num.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le numéro doit contenir exactement 8 chiffres.");
            return false;
        }

        if (!isValidCIN(tf_cin.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le CIN doit contenir exactement 8 chiffres.");
            return false;
        }

        if (!isValidZIP(tf_zip.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le code ZIP doit contenir 4 chiffres.");
            return false;
        }

        if (!isValidRIB(tf_rib.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le RIB doit contenir exactement 12 chiffres.");
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isValidPhoneNumber(String number) {
        return number.matches("\\d{8}");
    }

    private boolean isValidCIN(String cin) {
        return cin.matches("\\d{8}");
    }

    private boolean isValidZIP(String zip) {
        return zip.matches("\\d{4}");
    }

    private boolean isValidRIB(String rib) {
        return rib.matches("\\d{12}");
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
        tf_cin.clear();
        tf_adresse.clear();
        tf_city.clear();
        tf_zip.clear();
        tf_rib.clear();
        pdfFileBlob = null;
        pdfFileName= null;
    }
}

