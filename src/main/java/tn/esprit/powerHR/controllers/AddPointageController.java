package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.models.Pointage;
import tn.esprit.powerHR.services.ServicePointage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class AddPointageController {

    @FXML
    private Button bt_ajouterpointage;

    @FXML
    private Button bt_modiferpointage;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_supppointage;

    @FXML
    private ListView<Pointage> lv_pointage;

    @FXML
    private DatePicker dp_date;

    @FXML
    private TextField tf_heureEntree;

    @FXML
    private TextField tf_heureSortie;

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Pointage p;
    public void setListPointage(Pointage p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public Pointage getListPointage() {
        return p;
    }

    @FXML
    public void initialize() {
        ServicePointage ps = new ServicePointage();
        try {
            List<Pointage> list = ps.getAll();
            ObservableList<Pointage> observableList = FXCollections.observableList(list);
            lv_pointage.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void AjouterPointage(ActionEvent event) {
        ServicePointage ps = new ServicePointage();
        Pointage p = new Pointage();
        p.setDate(Date.valueOf(dp_date.getValue()));
        p.setHeureEntree(Time.valueOf(tf_heureEntree.getText()));
        p.setHeureSortie(Time.valueOf(tf_heureSortie.getText()));
        Employe employe = new Employe(1,"fdkbgkndfg","fdkbgkndfg","chargesRH",445.2,"123456789125","fdkbgkndfg");
        Paie paie = new Paie(1,0,0,null,null);
        p.setEmploye(employe);
        p.setPaie(paie);
        if(!tf_heureEntree.getText().isEmpty() && !tf_heureSortie.getText().isEmpty() && dp_date.getValue() != null) {
        try {
            ps.add(p);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Vérifiez les champs");
        }
    }

    @FXML
    void ModifierPage(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/UpdatePointage.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier Pointage");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void Supp(ActionEvent event) {
        setListPointage(lv_pointage.getSelectionModel().getSelectedItem());
        ServicePointage ps = new ServicePointage();
        try {
            ps.delete(getListPointage());
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }



}


