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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.services.ServiceEmploye;

import java.util.List;

public class EmployeModifController {

    @FXML
    private Button bt_ajouteremploye;

    @FXML
    private Button bt_modiferemploye;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_suppemploye;

    @FXML
    private ChoiceBox<Poste> cb_poste;

    @FXML
    private ListView<Employe> lv_Ajout;

    @FXML
    private TextField tf_CodeSociale;

    @FXML
    private TextField tf_pwd;

    @FXML
    private TextField tf_rib;

    @FXML
    private TextField tf_salaire;

    @FXML
    private TextField tf_username;

    ServiceEmploye se = new ServiceEmploye();

    @FXML
    private Employe e;
    public void setListEmploye(Employe e) {
        this.e = e;
        System.out.println("Received Id: " + e); // Debugging
    }
    public Employe getListEmploye() {
        return e;
    }

    @FXML
    public void initialize() {
        try {
            List<Employe> list = se.getAll();
            ObservableList<Employe> observableList = FXCollections.observableList(list);
            lv_Ajout.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void ChooseLine(MouseEvent event) {
        Employe e = lv_Ajout.getSelectionModel().getSelectedItem();
        tf_CodeSociale.setText(e.getCodeSociale());
        tf_username.setText(e.getUsername());
        tf_pwd.setText(e.getPassword());
        tf_salaire.setText(String.valueOf(e.getSalaire()));
        tf_rib.setText(e.getRib());
        setListEmploye(e);

    }

    @FXML
    void Modif(ActionEvent event) {
        Employe e = getListEmploye();
        e.setUsername( tf_username.getText() );
        e.setPassword(tf_pwd.getText());
        e.setCodeSociale(tf_CodeSociale.getText());
        e.setSalaire(Double.parseDouble(tf_salaire.getText()));
        e.setRib(tf_rib.getText());

        try {
            se.update(e);
            initialize();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }

    @FXML
    void NavAjout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Modif.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier Employe");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}