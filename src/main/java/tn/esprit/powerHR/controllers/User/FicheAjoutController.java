package tn.esprit.powerHR.controllers.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.User.FicheEmploye;
import tn.esprit.powerHR.services.User.ServiceFicheEmploye;
import tn.esprit.powerHR.utils.User.MinIOUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class FicheAjoutController implements Initializable {
    @FXML
    private TextField tf_cin, tf_email, tf_nom, tf_prenom, tf_adresse, tf_city, tf_zip, tf_numTel;
    @FXML
    private Button bt_submit, bt_upload;
    @FXML
    private ImageView backIcon;
    @FXML
    private AnchorPane mainPane;
    private String cvFileName;
    private String cvPdfUrl;

    private final ServiceFicheEmploye sf = new ServiceFicheEmploye();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearFields();
        handleBackButton();
    }

    @FXML
    private void handleBackButton() {
        backIcon.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ManageFiche.fxml"));
                Parent previousPage = loader.load();

                Stage stage = (Stage) mainPane.getScene().getWindow();
                Scene scene = new Scene(previousPage);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

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
        fe.setCvPdfUrl(cvPdfUrl);

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
        File selectedFile = fileChooser.showOpenDialog(bt_upload.getScene().getWindow());

        if (selectedFile != null) {
            try {
                cvFileName = selectedFile.getName();
                System.out.println("Selected CV file: " + cvFileName);

                String uploadedUrl = MinIOUtils.uploadCV("cvs", selectedFile.getAbsolutePath(), cvFileName);

                if (uploadedUrl == null || uploadedUrl.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Le fichier CV n'a pas été téléchargé correctement.");
                } else {
                    cvPdfUrl = uploadedUrl;
                    System.out.println("CV uploaded successfully: " + cvPdfUrl);
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "CV téléchargé avec succès !");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'upload du fichier CV.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun fichier sélectionné.");
        }
    }

    private boolean validateFields() {
        if (tf_nom.getText().isEmpty() || tf_prenom.getText().isEmpty() || tf_cin.getText().isEmpty()
                || tf_email.getText().isEmpty() || tf_numTel.getText().isEmpty() || bt_upload ==null
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
        tf_numTel.clear();
        tf_adresse.clear();
        tf_city.clear();
        tf_zip.clear();
        cvFileName = null;
    }
}
