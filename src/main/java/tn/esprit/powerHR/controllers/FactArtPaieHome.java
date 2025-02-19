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
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Article;
import tn.esprit.powerHR.models.Facture;
import tn.esprit.powerHR.models.Paiement;
import tn.esprit.powerHR.services.ServiceArticle;
import tn.esprit.powerHR.services.ServiceFacture;
import tn.esprit.powerHR.services.ServicePaiement;

import java.util.List;

public class FactArtPaieHome {

    @FXML
    private Button bt_article;

    @FXML
    private Button bt_fact;

    @FXML
    private Button bt_paiement;

    @FXML
    private ListView<Article> lv_article;

    @FXML
    private ListView<Facture> lv_fact;

    @FXML
    private ListView<Paiement> lv_paiement;

    @FXML
    public void initialize() {
        ServiceFacture ps = new ServiceFacture();
        ServiceArticle ps2 = new ServiceArticle();
        ServicePaiement ps3 = new ServicePaiement();
        try {
            List<Facture> list = ps.getAll();
            ObservableList<Facture> observableList = FXCollections.observableList(list);
            lv_fact.setItems(observableList);

            List<Article> list1 = ps2.getAll();
            ObservableList<Article> observableList1 = FXCollections.observableList(list1);
            lv_article.setItems(observableList1);

            List<Paiement> list2 = ps3.getAll();
            ObservableList<Paiement> observableList2 = FXCollections.observableList(list2);
            lv_paiement.setItems(observableList2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void NavArticle(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AjoutArticle.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajout Article");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void NavFact(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AjoutFacture.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajout Facture");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void NavPaie(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/AjoutPaiement.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajout Paiement");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
