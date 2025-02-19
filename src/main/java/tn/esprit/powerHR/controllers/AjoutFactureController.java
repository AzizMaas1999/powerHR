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
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Article;
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.models.Facture;
import tn.esprit.powerHR.models.Paiement;
import tn.esprit.powerHR.services.ServiceArticle;
import tn.esprit.powerHR.services.ServiceFacture;

import java.sql.Date;
import java.util.List;

public class AjoutFactureController {


    @FXML
    private Button bt_ajouterfacture;

    @FXML
    private Button bt_modiferfacture;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_suppfacture;

    @FXML
    private ComboBox<String> cb_typefact;

    @FXML
    private DatePicker dp_date;

    @FXML
    private TextField tf_num;

    @FXML
    private TextField tf_total;

    @FXML
    private ListView<Facture> lv_facture;

    private Facture p;
    public void setListFacture(Facture p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public Facture getListFacture() {
        return p;
    }

    @FXML
    public void initialize() {

        ObservableList<String> types = FXCollections.observableArrayList(
                "Facture",
                "Avoir"
        );
        cb_typefact.setItems(types);
        ServiceFacture ps = new ServiceFacture();
        try {
            List<Facture> list = ps.getAll();
            ObservableList<Facture> observableList = FXCollections.observableList(list);
            lv_facture.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void Ajout(ActionEvent event) {


        ServiceFacture fs = new ServiceFacture();
        Facture f = new Facture();
        f.setDate(Date.valueOf(dp_date.getValue()));
        f.setTypeFact( cb_typefact.getValue() );
        f.setNum( tf_num.getText() );
        try {
            f.setTotal(Float.parseFloat(tf_total.getText()));
        }catch (NumberFormatException e){
            System.out.println("error");
        }
       CLFr CL = new CLFr(1,null,null,null,null,null,null,null,null);
       f.setClFr(CL);
        Paiement p = new Paiement(1,null,null,null,null,0);
        f.setPaiement(p);


        try {
            fs.add(f);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void NavModif(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ModifFactuer.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier Facture");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void Supp(ActionEvent event) {
        setListFacture(lv_facture.getSelectionModel().getSelectedItem());
        ServiceFacture ps = new ServiceFacture();
        try {
            ps.delete(getListFacture());
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}


