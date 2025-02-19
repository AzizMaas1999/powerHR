package tn.esprit.powerHR.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.RepQuestionnaire;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.Questionnaire;
import tn.esprit.powerHR.services.ServiceRepQuestionnaire;

import java.sql.Date;
import java.util.List;

public class AjoutRepQ {

    @FXML
    private Button bt_ajouterreponse;

    @FXML
    private Button bt_modiferreponse;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_suppreponse;

    @FXML
    private DatePicker dc_date;

    @FXML
    private ListView<RepQuestionnaire> lv_repQ;

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
            ServiceRepQuestionnaire sq = new ServiceRepQuestionnaire();
            try {
                List<RepQuestionnaire> list = sq.getAll();
                ObservableList<RepQuestionnaire> observableList = FXCollections.observableList(list);
                lv_repQ.setItems(observableList);
            } catch (Exception e ) {
                System.out.println(e.getMessage());
            }}

        @FXML
        void AjouterReponse(ActionEvent event) {

            if (dc_date.getValue() == null || tf_Reponse.getText().isEmpty()) {
                System.out.println("Veuillez remplir tous les champs !");
                return;
            }
            ServiceRepQuestionnaire sq = new ServiceRepQuestionnaire();
            RepQuestionnaire rq = new RepQuestionnaire();

            rq.setDateCreation(Date.valueOf(dc_date.getValue()));
            rq.setReponse(tf_Reponse.getText());
            Questionnaire questionnaire = new Questionnaire(1,null,null,null,null);
            rq.setQuestionnaire(questionnaire);
            Employe employe = new Employe(2,"fdkbgkndfg","fdkbgkndfg","chargesRH",445.2,"123456789125","fdkbgkndfg");
            rq.setEmploye(employe);

            try {
                sq.add(rq);
                initialize();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    @FXML
    void ModifNav(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ModifRepQ.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier Reponse Questionnaire");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void Supp(ActionEvent event) {
        setListRepQuestionnaire(lv_repQ.getSelectionModel().getSelectedItem());
        ServiceRepQuestionnaire ps = new ServiceRepQuestionnaire();
        try {
            ps.delete(getListRepQuestionnaire());
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    }
