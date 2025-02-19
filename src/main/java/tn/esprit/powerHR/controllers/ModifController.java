package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Demande;
import tn.esprit.powerHR.services.DemandeService;

import java.sql.Date;
import java.util.List;

public class ModifController {

    @FXML
    private Button bt_ajouterdemande;

    @FXML
    private Button bt_modiferdemande;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_suppdemande;

    @FXML
    private ComboBox<String> cb_type;

    @FXML
    private DatePicker dc_date;

    @FXML
    private DatePicker dd_date;

    @FXML
    private DatePicker df_date;

    @FXML
    private ListView<Demande> lv_demande;

    @FXML
    private TextField tf_cause;

    @FXML
    private TextField tf_salaire;

    @FXML
    private TextField tf_status;

    private Demande p;
    public void setListDemande(Demande p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public Demande getListDemande() {
        return p;
    }

    @FXML
    public void initialize() {
        DemandeService ps = new DemandeService();
        try {
            List<Demande> list = ps.getAll();
            ObservableList<Demande> observableList = FXCollections.observableList(list);
            lv_demande.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void ChooseLine(MouseEvent event) {
        Demande p = lv_demande.getSelectionModel().getSelectedItem();
        dc_date.setValue(p.getDateCreation().toLocalDate());
        dd_date.setValue(p.getDateDebut().toLocalDate());
        df_date.setValue(p.getDateFin().toLocalDate());
        cb_type.setValue(p.getType());
        tf_cause.setText(p.getCause());
        tf_salaire.setText(String.valueOf(p.getSalaire()));
        tf_status.setText(p.getStatus());
        setListDemande(p);

    }

    @FXML
    void ModifierDemande(ActionEvent event) {
        DemandeService ps = new DemandeService();
        Demande d = getListDemande();
        d.setDateCreation(Date.valueOf(dc_date.getValue()));
        d.setType((String) cb_type.getValue());
        d.setDateDebut(Date.valueOf(dd_date.getValue()));
        d.setDateFin(Date.valueOf(df_date.getValue()));
        d.setSalaire(Float.parseFloat(tf_salaire.getText()));
        d.setCause(tf_cause.getText());
        d.setStatus(tf_status.getText());
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

}
