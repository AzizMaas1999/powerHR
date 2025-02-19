package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Candidat;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.FicheEmploye;
import tn.esprit.powerHR.services.ServiceCandidat;
import tn.esprit.powerHR.services.ServiceEmploye;
import tn.esprit.powerHR.services.ServiceFicheEmploye;

import java.util.List;

public class EmployeFicheCandidatHomeController {

    @FXML
    private Button bt_GererCandidat;

    @FXML
    private Button bt_GererFicheEmploye;

    @FXML
    private Button bt_gereremploye;

    @FXML
    private ListView<Candidat> lv_Candidat;

    @FXML
    private ListView<Employe> lv_Employe;

    @FXML
    private ListView<FicheEmploye> lv_Fiche;

    @FXML
    public void initialize() {
        ServiceEmploye ps = new ServiceEmploye();
        ServiceCandidat ps2 = new ServiceCandidat();
        ServiceFicheEmploye ps3 = new ServiceFicheEmploye();
        try {
            List<Employe> list = ps.getAll();
            ObservableList<Employe> observableList = FXCollections.observableList(list);
            lv_Employe.setItems(observableList);

            List<Candidat> list1 = ps2.getAll();
            ObservableList<Candidat> observableList1 = FXCollections.observableList(list1);
            lv_Candidat.setItems(observableList1);

            List<FicheEmploye> list2 = ps3.getAll();
            ObservableList<FicheEmploye> observableList2 = FXCollections.observableList(list2);
            lv_Fiche.setItems(observableList2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void NavCandidat(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AjoutCandidat.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajouter Candidat");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void NavEmploye(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Ajout.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajouter Employe");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void NavFiche(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AjoutFiche.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajouter Fiche Employe");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
