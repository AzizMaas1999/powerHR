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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.User.Candidat;
import tn.esprit.powerHR.models.Entreprise;
import tn.esprit.powerHR.services.User.ServiceCandidat;
import tn.esprit.powerHR.services.EntrepriseService;
import tn.esprit.powerHR.utils.User.MinIOUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    private ComboBox<String> cb_entreprise;
    @FXML
    private Button bt_ajouterCandidat;
    @FXML
    private ImageView backIcon;
    @FXML
    private AnchorPane MainPane;

    private String cvFileName;
    private String cvPdfUrl; // Add this for storing the URL

    private ServiceCandidat sc = new ServiceCandidat();
    private EntrepriseService se = new EntrepriseService();

    @FXML
    private void initialize() {
        loadEntreprises();
        clearFields();
        handleBackButton();

        bt_upload.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner un CV (PDF)");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File selectedFile = fileChooser.showOpenDialog(bt_upload.getScene().getWindow());

            if (selectedFile != null) {
                try {
                    cvFileName = selectedFile.getName();
                    System.out.println("Selected CV file: " + cvFileName);

                    // Upload the CV to MinIO and get the URL
                    String uploadedUrl = MinIOUtils.uploadCV("cv-bucket", selectedFile.getAbsolutePath(), cvFileName);

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
        });
    }
    @FXML
    private void handleBackButton() {
        backIcon.setOnMouseClicked(event -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/MangeCandidat.fxml"));
                Parent previousPage = loader.load();

                Stage stage = (Stage) MainPane.getScene().getWindow();
                Scene scene = new Scene(previousPage);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

        private void loadEntreprises() {
        List<Entreprise> entreprises = se.getAll(); // Get all entreprises
        ObservableList<String> entrepriseNames = FXCollections.observableArrayList(
                entreprises.stream().map(Entreprise::getNom).toList()
        );
        cb_entreprise.setItems(entrepriseNames);
    }

    @FXML
    private void AjouterCandidat(ActionEvent event) {
        if (tf_nom.getText().isEmpty() || tf_prenom.getText().isEmpty() || tf_email.getText().isEmpty() ||
                tf_Num.getText().isEmpty() || cb_entreprise.getValue() == null ) {
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
        String entrepriseName = cb_entreprise.getValue(); // Get the selected entreprise name

        // Retrieve the actual Entreprise object from the database
        List<Entreprise> entreprises = se.getAll();
        Entreprise selectedEntreprise = entreprises.stream()
                .filter(e -> e.getNom().equals(entrepriseName))
                .findFirst()
                .orElse(null);

        if (selectedEntreprise == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "L'entreprise sélectionnée est invalide.");
            return;
        }

        // Debugging line to check the CV URL before saving it
        System.out.println("Final CV URL: " + cvPdfUrl);

        if (cvPdfUrl == null || cvPdfUrl.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le chemin du fichier CV est vide.5555");
            return;
        }

        Candidat candidat = new Candidat();
        candidat.setNom(nom);
        candidat.setPrenom(prenom);
        candidat.setEmail(email);
        candidat.setTelephone(telephone);
        candidat.setCvPdfUrl(cvPdfUrl); // Set the CV URL
        candidat.setEntreprise(selectedEntreprise); // Set the actual Entreprise object

        try {
            sc.add(candidat);
            //clearFields();
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
        cb_entreprise.getSelectionModel().clearSelection();
        cvFileName = null; // Clear the file name as well
        cvPdfUrl = null; // Clear the URL as well
    }
}
