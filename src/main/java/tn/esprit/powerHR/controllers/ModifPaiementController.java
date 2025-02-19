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
import tn.esprit.powerHR.models.Paiement;
import tn.esprit.powerHR.services.ServicePaiement;

import java.sql.Date;
import java.util.List;

public class ModifPaiementController {

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
            lv_Paiement.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private DatePicker dp_date;

    @FXML
    private ListView<Paiement> lv_Paiement;


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
    void ChooseLine(MouseEvent event) {
        Paiement p = lv_Paiement.getSelectionModel().getSelectedItem();
        dp_date.setValue(p.getDate().toLocalDate());
        cb_mode.setValue(p.getMode());
        tf_montant.setText(p.getMontant() + "");
        setListPaiement(p);

    }

    @FXML
    void Modifer(ActionEvent event) {
        ServicePaiement ps = new ServicePaiement();
        Paiement a = getListPaiement();
        p.setDate(Date.valueOf(dp_date.getValue()));
        p.setMode(cb_mode.getValue());
        p.setReference(tf_reference.getText());
        p.setMontant(Double.parseDouble(tf_montant.getText()));

        try {
            ps.update(a);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void NavAjout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AjoutPaiement.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajouter Paiement");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }


}
