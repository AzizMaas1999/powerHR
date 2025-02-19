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
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Questionnaire;
import tn.esprit.powerHR.services.ServiceQuestionnaire;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.util.List;

public class ModifQ {

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
    private ListView<Questionnaire> lv_ques;

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

    @FXML
    public void initialize() {
        ServiceQuestionnaire qs = new ServiceQuestionnaire();
        try {
            List<Questionnaire> list = qs.getAll();
            ObservableList<Questionnaire> observableList = FXCollections.observableList(list);
            lv_ques.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void ChooseLine(MouseEvent event) {
       Questionnaire q = lv_ques.getSelectionModel().getSelectedItem();
            dc_date.setValue(q.getDateCreation().toLocalDate());
            tf_Objet.setText(q.getObjet());
            tf_desc.setText(q.getDescription());
            setListquestionnaire(q);
        }


    @FXML
    void ModifierQuestionnaire(ActionEvent event) {
        ServiceQuestionnaire ps = new ServiceQuestionnaire();
        Questionnaire q = getListquestionnaire();
        q.setDateCreation(Date.valueOf(dc_date.getValue()));
        q.setObjet(tf_Objet.getText());
        q.setDescription(tf_desc.getText());
        try {
            ps.update(q);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void NavigateAjout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AjoutQ.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ajouter Questionnaire");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

