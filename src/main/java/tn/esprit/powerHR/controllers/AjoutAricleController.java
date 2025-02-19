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
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Article;
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.models.Facture;
import tn.esprit.powerHR.models.Paiement;
import tn.esprit.powerHR.services.ServiceArticle;
import tn.esprit.powerHR.services.ServiceFacture;

import java.sql.Date;
import java.util.List;


public class AjoutAricleController {

    @FXML
    private Button bt_ajouterarticle;

    @FXML
    private Button bt_modiferarticle;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_supparticle;

    @FXML
    private TextField tf_quantity;

    @FXML
    public void initialize() {

        ServiceArticle ps = new ServiceArticle();
        try {
            List<Article> list = ps.getAll();
            ObservableList<Article> observableList = FXCollections.observableList(list);
            lv_article.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private TextField tf_description;

    @FXML
    private TextField tf_prixuni;

    @FXML
    private TextField tf_tva;

    @FXML
    private ListView<Article> lv_article;

    private Article p;
    public void setListArticle(Article p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public Article getListArticle() {
        return p;
    }


    @FXML
    void Ajout(ActionEvent event) {
        ServiceArticle as = new ServiceArticle();
        Article a = new Article();
        a.setDescription( tf_description.getText() );
        try {
            a.setQuantity(Integer.parseInt(tf_quantity.getText()));
        }catch (NumberFormatException e){
            System.out.println("error");
        }

            try {
                a.setPrixUni(Float.parseFloat(tf_prixuni.getText()));
            }catch (NumberFormatException e){
                System.out.println("error");
            }

            try {
                a.setTVA(Float.parseFloat(tf_tva.getText()));
            }catch (NumberFormatException e){
                System.out.println("error");
            }

        Facture facture = new Facture(1,null,null,null,0,null,null,null);
        a.setFacture(facture);


        try {
            as.add(a);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void NavModif(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ModifArticle.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier Article");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


    }

    @FXML
    void Supp(ActionEvent event) {
        setListArticle(lv_article.getSelectionModel().getSelectedItem());
        ServiceArticle ps = new ServiceArticle();
        try {
            ps.delete(getListArticle());
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }


}
