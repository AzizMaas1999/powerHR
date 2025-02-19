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
import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.models.Pointage;
import tn.esprit.powerHR.services.ServicePaie;
import tn.esprit.powerHR.services.ServicePointage;

import java.util.List;

public class HomePaiePointageController {

    @FXML
    private Button bt_ajouterpointage;

    @FXML
    private Button bt_modiferpointage;

    @FXML
    private ListView<Paie> lv_paie;

    @FXML
    private ListView<Pointage> lv_pointage;

    @FXML
    public void initialize() {
        ServicePointage ps = new ServicePointage();
        ServicePaie ps2 = new ServicePaie();
        try {
            List<Pointage> list = ps.getAll();
            ObservableList<Pointage> observableList = FXCollections.observableList(list);
            lv_pointage.setItems(observableList);

            List<Paie> list1 = ps2.getAll();
            ObservableList<Paie> observableList1 = FXCollections.observableList(list1);
            lv_paie.setItems(observableList1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void AjoutPage(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AddPointage.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Gerer Pointage");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void ModifierPage(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AddPaie.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Gerer Paie");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
