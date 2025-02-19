package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Article;
import tn.esprit.powerHR.services.ServiceArticle;

import java.util.List;

public class ModifArticleController {

    @FXML
    private Button bt_ajouterarticle;

    @FXML
    private Button bt_modiferarticle;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_supparticle;

    @FXML
    private ListView<Article> lv_article;

    @FXML
    private TextField tf_description;

    @FXML
    private TextField tf_prixuni;

    @FXML
    private TextField tf_quantity;

    @FXML
    private TextField tf_tva;
    private Article p;
    public void setListArticle(Article p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public Article getListArticle() {
        return p;
    }

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
    void ChooseLine(MouseEvent event) {
        Article p = lv_article.getSelectionModel().getSelectedItem();
        tf_description.setText(p.getDescription());
        tf_quantity.setText(String.valueOf(p.getQuantity()));
        tf_prixuni.setText(String.valueOf(p.getPrixUni()));
        tf_tva.setText(String.valueOf(p.getTVA()));
        setListArticle(p);

    }

    @FXML
    void Modif(ActionEvent event) {
        ServiceArticle ps = new ServiceArticle();
        Article a = getListArticle();
        a.setDescription( tf_description.getText() );
        a.setQuantity(Integer.parseInt(tf_quantity.getText()));
        a.setPrixUni(Float.parseFloat(tf_prixuni.getText()));
        a.setTVA(Float.parseFloat(tf_tva.getText()));
        try {
            ps.update(a);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void NavAjout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AjoutArticle.fxml"));
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

}
