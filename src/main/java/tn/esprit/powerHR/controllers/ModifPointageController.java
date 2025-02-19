package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Pointage;
import tn.esprit.powerHR.services.ServicePointage;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ModifPointageController {

    @FXML
    private Button bt_ajouterpointage;

    @FXML
    private Button bt_modiferpointage;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_supppointage;

    @FXML
    private DatePicker dp_date;

    @FXML
    private ListView<Pointage> lv_pointage;

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
    void ChooseLine(MouseEvent event) {
        Pointage p = lv_pointage.getSelectionModel().getSelectedItem();
        dp_date.setValue(p.getDate().toLocalDate());
        tf_heureEntree.setText(p.getHeureEntree().toString());
        tf_heureSortie.setText(p.getHeureSortie().toString());
        setListPointage(p);

    }

    @FXML
    void ModifSubmit(ActionEvent event) {
        ServicePointage ps = new ServicePointage();
        Pointage p = getListPointage();
        p.setDate(Date.valueOf(dp_date.getValue()));
        p.setHeureEntree(Time.valueOf(tf_heureEntree.getText()));
        p.setHeureSortie(Time.valueOf(tf_heureSortie.getText()));
        p.setEmploye(p.getEmploye());
        p.setPaie(p.getPaie());
        if(!tf_heureEntree.getText().isEmpty() && !tf_heureSortie.getText().isEmpty() && dp_date.getValue() != null) {
        try {
            ps.update(p);
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
    void NavAdd(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AddPointage.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajouter Pointage");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
