package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.models.Facture;
import tn.esprit.powerHR.models.Paiement;
import tn.esprit.powerHR.services.ServiceFacture;

import java.sql.Date;
import java.util.List;

public class ModifFactureController {

    @FXML
    private Button bt_ajouterfacture;

    @FXML
    private Button bt_modiferfacture;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_suppfacture;

    @FXML
    private ComboBox<String> cb_typefact;
    @FXML
    public void initialize() {

        ObservableList<String> types = FXCollections.observableArrayList(
                "Facture",
                "Avoir",
                " "
        );
        cb_typefact.setItems(types);
        ServiceFacture ps = new ServiceFacture();
        try {
            List<Facture> list = ps.getAll();
            ObservableList<Facture> observableList = FXCollections.observableList(list);
            lv_facture.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private DatePicker dp_date;

    @FXML
    private TextField tf_num;

    @FXML
    private TextField tf_total;

    @FXML
    private ListView<Facture> lv_facture;

    @FXML
    void Modif(ActionEvent event) {


            // Création du ServiceFacture pour gérer la modification
            ServiceFacture fs = new ServiceFacture();

            // Vérification de la sélection de la facture dans la ListView
            Facture factureSelectionnee = lv_facture.getSelectionModel().getSelectedItem();
            if (factureSelectionnee == null) {
                System.out.println("Veuillez sélectionner une facture à modifier.");
                return;
            }

            // Vérification des champs du formulaire
            if (dp_date.getValue() == null || cb_typefact.getValue() == null || tf_num.getText().isEmpty() || tf_total.getText().isEmpty()) {
                System.out.println("Tous les champs sont obligatoires !");
                return;
            }

            // Mise à jour des données de la facture sélectionnée
            factureSelectionnee.setDate(Date.valueOf(dp_date.getValue()));
            factureSelectionnee.setTypeFact(cb_typefact.getValue());
            factureSelectionnee.setNum(tf_num.getText());

            try {
                // Conversion sécurisée du total en float
                factureSelectionnee.setTotal(Float.parseFloat(tf_total.getText()));
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide pour le total.");
                return;
            }

            // Mise à jour des relations avec CLFr et Paiement
            CLFr CL = new CLFr(1, null, null, null, null, null, null, null, null);
            factureSelectionnee.setClFr(CL);

            Paiement p = new Paiement(1, null, null, null, null, 0);
            factureSelectionnee.setPaiement(p);

            // Tentative de modification de la facture
            try {
                fs.update(factureSelectionnee);
                System.out.println("Facture modifiée avec succès !");

                // Réinitialisation du formulaire après modification
                dp_date.setValue(null);
                cb_typefact.setValue(null);
                tf_num.clear();
                tf_total.clear();

                // Rafraîchissement de la ListView après modification
                initialize();
            } catch (Exception e) {
                System.out.println("Erreur lors de la modification de la facture : " + e.getMessage());
            }
        }

    @FXML
    void NavAjout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AjoutFacture.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajouter Facture");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }


    public void handleItemClick(MouseEvent mouseEvent) {
        Facture selectedFacture = lv_facture.getSelectionModel().getSelectedItem();

        if (selectedFacture != null) {
            dp_date.setValue(selectedFacture.getDate().toLocalDate());
            cb_typefact.setValue(selectedFacture.getTypeFact());
            tf_num.setText(selectedFacture.getNum());
            tf_total.setText(String.valueOf(selectedFacture.getTotal()));
        }
    }
}



