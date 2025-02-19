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
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.services.ServiceCLFr;

import java.util.List;

public class ModifierCLFr {

    @FXML
    private TextField NumTel;

    @FXML
    private TextField adresse;

    @FXML
    private Button bt_ajouterCLFr;

    @FXML
    private Button bt_modiferCLFr;

    @FXML
    private Button bt_submit2;

    @FXML
    private Button bt_supprimerCLFr;

    @FXML
    private ListView<CLFr> lv_ShowCLFr;

    @FXML
    private TextField matriculeFicale;

    @FXML
    private TextField nom;

    @FXML
    private ChoiceBox<String> type2;

    private CLFr p;
    public void setListCLFr(CLFr p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public CLFr getListCLFr() {
        return p;
    }

    @FXML
    public void initialize() {
        ServiceCLFr ps = new ServiceCLFr();
        try {
            List<CLFr> list = ps.getAll();
            ObservableList<CLFr> observableList = FXCollections.observableList(list);
            lv_ShowCLFr.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void ChooseLine(MouseEvent event) {
        CLFr p = lv_ShowCLFr.getSelectionModel().getSelectedItem();
        nom.setText(p.getNom());
        matriculeFicale.setText(p.getMatriculeFiscale());
        adresse.setText(p.getAdresse());
        NumTel.setText(p.getNumTel());
        type2.setValue(p.getType());
        setListCLFr(p);

    }


    @FXML
    void ModifierCLFr(ActionEvent event) {
        ServiceCLFr ps = new ServiceCLFr();
        CLFr clfr = getListCLFr();
        clfr.setNom(nom.getText());
        clfr.setMatriculeFiscale(matriculeFicale.getText());
        clfr.setAdresse(adresse.getText());
        clfr.setNumTel(NumTel.getText());
        clfr.setType(type2.getValue());
        try {
            ps.update(p);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void NavigateAjouter(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ajouterCLFr.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier ClFr");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
