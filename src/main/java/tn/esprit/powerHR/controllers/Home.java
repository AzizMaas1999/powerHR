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
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.models.Feedback;
import tn.esprit.powerHR.services.ServiceCLFr;
import tn.esprit.powerHR.services.ServiceFeedback;

import java.util.List;

public class Home {

    @FXML
    private Button bt_gererClfr;

    @FXML
    private Button bt_gererFeedback;

    @FXML
    private ListView<CLFr> lv_ShowCLFr;

    @FXML
    private ListView<Feedback> lv_ShowFeedbck;

    @FXML
    public void initialize() {
        ServiceCLFr ps = new ServiceCLFr();
        ServiceFeedback ps2 = new ServiceFeedback();
        try {
            List<Feedback> list = ps2.getAll();
            ObservableList<Feedback> observableList = FXCollections.observableList(list);
            lv_ShowFeedbck.setItems(observableList);

            List<CLFr> list1 = ps.getAll();
            ObservableList<CLFr> observableList1 = FXCollections.observableList(list1);
            lv_ShowCLFr.setItems(observableList1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void NavigateClfr(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ajouterCLFr.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajouter CLFr");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void NavigateFeedback(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ajouterFeedback.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajouter Feedback");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
