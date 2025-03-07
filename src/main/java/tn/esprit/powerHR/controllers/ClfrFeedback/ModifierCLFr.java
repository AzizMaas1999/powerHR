package tn.esprit.powerHR.controllers.ClfrFeedback;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.ClfrFeedback.CLFr;
import tn.esprit.powerHR.services.ClfrFeedback.ServiceCLFr;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import java.io.File;
public class ModifierCLFr {

    @FXML
    private TextField NumTel;

    @FXML
    private TextField adresse;

    @FXML
    private TextField matriculeFicale;

    @FXML
    private TextField nom;

    @FXML
    private ChoiceBox<String> type2;
    @FXML
    private TextField photoPathField;
    private CLFr selectedCLFr;
    private AjouterCLFr parentController;
    // Method for the "Ajouter" button
    public void NavigateAjouter(ActionEvent event) {
        // Add your code to handle the "Ajouter" action
    }

    // Method for the "Supprimer" button
    public void Supp(ActionEvent event) {
        // Add your code to handle the "Supprimer" action
    }

    // Method for the "Choisir Photo" button
    public void choisirPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        // Add your code to handle the "Choisir Photo" action
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            photoPathField.setText(file.getAbsolutePath());
        }
    }

    // Method for the "Modifier" button




    // Initialise les données et le parent
    public void initData(CLFr clfr, AjouterCLFr parent) {
        this.selectedCLFr = clfr;
        this.parentController = parent;

        // Initialiser les choix du type
        type2.getItems().setAll("Client", "Fournisseur"); // Ajouté
        // Remplir les champs
        nom.setText(clfr.getNom());
        matriculeFicale.setText(clfr.getMatriculeFiscale());
        adresse.setText(clfr.getAdresse());
        NumTel.setText(clfr.getNumTel());
        type2.setValue(clfr.getType());
        photoPathField.setText(clfr.getPhotoPath());
    }
    @FXML
    void ModifierCLFr(ActionEvent event) {
        try {
            // Mettre à jour l'objet
            selectedCLFr.setNom(nom.getText());
            selectedCLFr.setMatriculeFiscale(matriculeFicale.getText());
            selectedCLFr.setAdresse(adresse.getText());
            selectedCLFr.setNumTel(NumTel.getText());
            selectedCLFr.setType(type2.getValue());
            selectedCLFr.setPhotoPath(photoPathField.getText());

            // Appeler le service de mise à jour
            ServiceCLFr service = new ServiceCLFr();
            service.update(selectedCLFr);

            // Rafraîchir la ListView dans AjouterCLFr
            parentController.refreshListView();

            // Fermer la fenêtre
            ((Stage) nom.getScene().getWindow()).close();

        } catch (Exception e) {
            System.out.println("Erreur modification: " + e.getMessage());
        }
    }


    @FXML
    void Annuler(ActionEvent event) {
        ((Stage) nom.getScene().getWindow()).close();
    }
    // Méthodes non utilisées (à conserver pour la compatibilité FXML)
  //  public void NavigateAjouter(ActionEvent event) {}
  //  public void Supp(ActionEvent event) {}
}


