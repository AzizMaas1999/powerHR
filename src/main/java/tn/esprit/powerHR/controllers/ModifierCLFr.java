package tn.esprit.powerHR.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.services.ServiceCLFr;

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
        // Add your code to handle the "Choisir Photo" action
    }

    // Method for the "Modifier" button




    // Initialise les données et le parent
    public void initData(CLFr clfr, AjouterCLFr parent) {
        this.selectedCLFr = clfr;
        this.parentController = parent;

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
}


