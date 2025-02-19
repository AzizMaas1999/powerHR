package tn.esprit.powerHR.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Article;
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.models.Facture;
import tn.esprit.powerHR.models.Paiement;
import tn.esprit.powerHR.services.ServiceArticle;
import tn.esprit.powerHR.services.ServiceFacture;
import tn.esprit.powerHR.services.ServicePaiement;

import java.sql.Date;
import java.util.List;

public class AjoutPaiementController {

    @FXML
    private Button bt_ajouterpaiement;

    @FXML
    private Button bt_modiferpaiement;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_supppaiement;

    @FXML
    private ComboBox<String> cb_mode;

    @FXML
    public void initialize() {

        ObservableList<String> modes = FXCollections.observableArrayList(
                "cheque",
                "espece",
                "virement",
                "traite"
        );
        cb_mode.setItems(modes);
        ServicePaiement ps = new ServicePaiement();
        try {
            List<Paiement> list = ps.getAll();
            ObservableList<Paiement> observableList = FXCollections.observableList(list);
            lv_paiement.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


   @FXML
   private DatePicker dp_date;

    @FXML
    private ListView<Paiement> lv_paiement;

    @FXML
    private TextField tf_montant;

    @FXML
    private TextField tf_reference;

    private Paiement p;
    public void setListPaiement(Paiement p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public Paiement getListPaiement() {
        return p;
    }

    @FXML
    void Ajout(ActionEvent event) {
        ServicePaiement ps = new ServicePaiement();
        Paiement p = new Paiement();
        p.setDate(Date.valueOf(dp_date.getValue()));
        p.setMode(cb_mode.getValue());

        try {
            p.setMontant(Double.parseDouble(tf_montant.getText()));
        }catch (NumberFormatException e){
            System.out.println("error");
        }

        Facture facture = new Facture(1,null,null,null,0,null,null,null);
        p.setReference(tf_reference.getText());


        try {
            ps.add(p);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void NavModif(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ModifPaiement.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier Paiement");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void Supp(ActionEvent event) {
        setListPaiement(lv_paiement.getSelectionModel().getSelectedItem());
        ServicePaiement ps = new ServicePaiement();
        try {
            ps.delete(getListPaiement());
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}


