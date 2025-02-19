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
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Demande;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.Questionnaire;
import tn.esprit.powerHR.services.DemandeService;
import tn.esprit.powerHR.services.ServiceQuestionnaire;

import java.sql.Date;
import java.util.List;

public class AjoutQ {

    @FXML
    private Button bt_ajouterQuestionnaire;

    @FXML
    private Button bt_modiferquestionnaire;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_supppquestionnaire;

    @FXML
    private DatePicker dc_date;

    @FXML
    private ListView<Questionnaire> lv_ajoutQ;

    @FXML
    private TextField tf_Objet;

    @FXML
    private TextField tf_desc;

    private Questionnaire q;

    public void setListquestionnaire(Questionnaire q) {
        this.q = q;
        System.out.println("Received Id: " + q);
    }

    public Questionnaire getListquestionnaire() {
        return q;
    }

    public void initialize() {
        ServiceQuestionnaire sq = new ServiceQuestionnaire();
        try {
            List<Questionnaire> list = sq.getAll();
            ObservableList<Questionnaire> observableList = FXCollections.observableList(list);
            lv_ajoutQ.setItems(observableList);
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }}

    @FXML
    void AjouterQuestionnaire(ActionEvent event) {

        if (dc_date.getValue() == null || tf_Objet.getText().isEmpty() || tf_desc.getText().isEmpty()) {
            System.out.println("Veuillez remplir tous les champs !");
            return;
        }

        ServiceQuestionnaire sq = new ServiceQuestionnaire();
        Questionnaire q = new Questionnaire();
        q.setDateCreation(Date.valueOf(dc_date.getValue()));
        q.setObjet(tf_Objet.getText());
        q.setDescription(tf_desc.getText());

        try {
            sq.add(q);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void NavigateModif(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ModifQ.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier Questionnaire");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }}
        @FXML
        void Supp(ActionEvent event){
            setListquestionnaire(lv_ajoutQ.getSelectionModel().getSelectedItem());
            ServiceQuestionnaire ps = new ServiceQuestionnaire();
            try {
                ps.delete(getListquestionnaire());
                initialize();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
}


