package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Feedback;
import tn.esprit.powerHR.models.Feedback;
import tn.esprit.powerHR.services.ServiceFeedback;
import tn.esprit.powerHR.services.ServiceFeedback;

import java.sql.Date;
import java.util.List;

public class UpdateFeedback {

    @FXML
    private DatePicker Date_creation;

    @FXML
    private TextField Description;

    @FXML
    private Button bt_ajouterfeedback;

    @FXML
    private Button bt_modiferfeedback;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_supppfeedback;

    @FXML
    private ListView<Feedback> lv_ShowFeedback;

    @FXML
    private ChoiceBox<String> type;

    private Feedback p;
    public void setListFeedback(Feedback p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public Feedback getListFeedback() {
        return p;
    }

    @FXML
    public void initialize() {
        ServiceFeedback ps = new ServiceFeedback();
        try {
            List<Feedback> list = ps.getAll();
            ObservableList<Feedback> observableList = FXCollections.observableList(list);
            lv_ShowFeedback.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void ChooseLine(MouseEvent event) {
        Feedback p = lv_ShowFeedback.getSelectionModel().getSelectedItem();
        Date_creation.setValue(p.getDateCreation().toLocalDate());
        Description.setText(p.getDescription());
        type.setValue(p.getType());
        setListFeedback(p);

    }

    @FXML
    void ModifFeedBack(ActionEvent event) {
        ServiceFeedback ps = new ServiceFeedback();
        Feedback feedback = getListFeedback();
        feedback.setDateCreation(Date.valueOf(Date_creation.getValue()));
        feedback.setDescription(Description.getText());
        feedback.setType(type.getValue());
        try {
            ps.update(p);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void NavigateAjout(ActionEvent event) {
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
