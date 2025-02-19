package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.services.ServiceCLFr;

import java.util.List;

public class AjouterCLFr {

    @FXML
    private TextField NumTel;

    @FXML
    private TextField adresse;

    @FXML
    private Button bt_ajouterCLFr;

    @FXML
    private Button bt_modiferCLFr;

    @FXML
    private Button bt_submit2;

    @FXML
    private Button bt_supprimerCLFr;

    @FXML
    private ListView<CLFr> lv_ShowCLFr;

    @FXML
    private TextField matriculeFicale;

    @FXML
    private TextField nom;

    // La ChoiceBox contient désormais "Client" et "Fournisseur"
    @FXML
    private ChoiceBox<String> type2;

    private CLFr p;
    public void setListClFr(CLFr p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public CLFr getListClFr() {
        return p;
    }



    @FXML
    public void initialize() {
        // Remplissage de la ChoiceBox
        type2.getItems().addAll("Client", "Fournisseur");
        // Chargement initial de la ListView
        ServiceCLFr serviceCLFr = new ServiceCLFr();
        try {
            List<CLFr> list = serviceCLFr.getAll();
            ObservableList<CLFr> observableList = FXCollections.observableList(list);
            lv_ShowCLFr.setItems(observableList);
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des CLFr : " + e.getMessage());
        }
    }


    @FXML
    void AjouterCLFr(ActionEvent event) {
        ServiceCLFr serviceCLFr = new ServiceCLFr();
        try {
            // Vérification que tous les champs sont remplis
            if (nom.getText().trim().isEmpty() ||
                    matriculeFicale.getText().trim().isEmpty() ||
                    adresse.getText().trim().isEmpty() ||
                    NumTel.getText().trim().isEmpty() ||
                    type2.getValue() == null) {

                // Affichage de l'alerte si un champ est vide
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Champ manquant");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;  // Arrêter l'exécution si la saisie est invalide
            }

            // Création et affectation des valeurs à l'objet CLFr
            CLFr clfr = new CLFr();
            clfr.setNom(nom.getText());
            clfr.setMatriculeFiscale(matriculeFicale.getText());
            clfr.setAdresse(adresse.getText());
            clfr.setNumTel(NumTel.getText());
            clfr.setType(type2.getValue()); // "Client" ou "Fournisseur"

            // Simulation d'un objet Employe (à remplacer par une récupération réelle si nécessaire)
            Employe employe = new Employe(1, "fdkbgkndfg", "fdkbgkndfg", "chargesRH", 445.2, "123456789125", "fdkbgkndfg");
            clfr.setEmploye(employe);

            // Ajout du CLFr via le service
            serviceCLFr.add(clfr);
            System.out.println("CLFr ajouté avec succès !");

            // Rafraîchissement de la ListView pour afficher le nouveau CLFr
            initialize();

            // Affichage d'une alerte de succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Ajout réussi");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Le CLFr a été ajouté avec succès.");
            successAlert.showAndWait();
            nom.clear();
            matriculeFicale.clear();
            adresse.clear();
            NumTel.clear();
            type2.setValue(null);

        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout du CLFr : " + e.getMessage());

            // Affichage d'une alerte en cas d'erreur
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Une erreur est survenue lors de l'ajout du CLFr.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    void NavigateModifier(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/modifierCLFr.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier ClFr");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void Supp(ActionEvent event) {
        setListClFr(lv_ShowCLFr.getSelectionModel().getSelectedItem());
        ServiceCLFr ps = new ServiceCLFr();
        try {
            ps.delete(getListClFr());
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
