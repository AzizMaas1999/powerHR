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
import tn.esprit.powerHR.models.Demande;
import tn.esprit.powerHR.models.Questionnaire;
import tn.esprit.powerHR.models.RepQuestionnaire;
import tn.esprit.powerHR.services.DemandeService;
import tn.esprit.powerHR.services.ServiceQuestionnaire;
import tn.esprit.powerHR.services.ServiceRepQuestionnaire;

import java.util.List;

public class DemQuestRepHomeController {

    @FXML
    private Button bt_GererDemande;

    @FXML
    private Button bt_GererQuestionnaire;

    @FXML
    private Button bt_GererRepQuestionnaire;

    @FXML
    private ListView<Demande> lv_Dem;

    @FXML
    private ListView<Questionnaire> lv_Quest;

    @FXML
    private ListView<RepQuestionnaire> lv_RepQ;

    @FXML
    public void initialize() {
        DemandeService ps = new DemandeService();
        ServiceQuestionnaire ps2 = new ServiceQuestionnaire();
        ServiceRepQuestionnaire ps3 = new ServiceRepQuestionnaire();
        try {
            List<Demande> list = ps.getAll();
            ObservableList<Demande> observableList = FXCollections.observableList(list);
            lv_Dem.setItems(observableList);

            List<Questionnaire> list1 = ps2.getAll();
            ObservableList<Questionnaire> observableList1 = FXCollections.observableList(list1);
            lv_Quest.setItems(observableList1);

            List<RepQuestionnaire> list3 = ps3.getAll();
            ObservableList<RepQuestionnaire> observableList3 = FXCollections.observableList(list3);
            lv_RepQ.setItems(observableList3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void DemandeNav(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AjoutD.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajouter Demande");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void QuestionnaireNav(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AjoutQ.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajouter Questionnaire");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void RepNav(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/listequest.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajouter Reponse Questionnaire");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
