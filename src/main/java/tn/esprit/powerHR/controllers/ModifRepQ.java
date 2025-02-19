package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.RepQuestionnaire;
import tn.esprit.powerHR.services.ServiceRepQuestionnaire;

import java.sql.Date;
import java.util.List;

public class ModifRepQ {

    @FXML
    private Button bt_ajouterreponse;

    @FXML
    private Button bt_modiferrponse;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_supreponse;

    @FXML
    private DatePicker dc_date;

    @FXML
    private ListView<RepQuestionnaire> lv_RepQ;

    @FXML
    private TextField tf_Reponse;

    private RepQuestionnaire p;
    public void setListRepQuestionnaire(RepQuestionnaire p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public RepQuestionnaire getListRepQuestionnaire() {
        return p;
    }

    @FXML
    public void initialize() {
        ServiceRepQuestionnaire ps = new ServiceRepQuestionnaire();
        try {
            List<RepQuestionnaire> list = ps.getAll();
            ObservableList<RepQuestionnaire> observableList = FXCollections.observableList(list);
            lv_RepQ.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void ChooseLine(MouseEvent event) {
        RepQuestionnaire rq = lv_RepQ.getSelectionModel().getSelectedItem();
        rq.setDateCreation(Date.valueOf(dc_date.getValue()));
        rq.setReponse(tf_Reponse.getText());
        setListRepQuestionnaire(p);

    }

    @FXML
    void AjoutNav(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AjoutRepQ.fxml"));
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

    @FXML
    void ModiferReponse(ActionEvent event) {
        ServiceRepQuestionnaire ps = new ServiceRepQuestionnaire();
        RepQuestionnaire rq = getListRepQuestionnaire();
        rq.setDateCreation(Date.valueOf(dc_date.getValue()));
        rq.setReponse(tf_Reponse.getText());
        try {
            ps.update(p);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
