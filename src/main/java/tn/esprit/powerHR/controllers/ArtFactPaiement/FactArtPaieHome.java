package tn.esprit.powerHR.controllers.ArtFactPaiement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tn.esprit.powerHR.models.ArtFactPaiement.Article;
import tn.esprit.powerHR.models.ArtFactPaiement.Facture;
import tn.esprit.powerHR.models.ArtFactPaiement.Paiement;
import tn.esprit.powerHR.services.ArtFactPaiement.ServiceArticle;
import tn.esprit.powerHR.services.ArtFactPaiement.ServiceFacture;
import tn.esprit.powerHR.services.ArtFactPaiement.ServicePaiement;

import java.io.IOException;
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
    private AnchorPane mainPane;


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
        try {
            // Load the addEmploye.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/AjoutArticle.fxml"));
            Parent addEmployeView = loader.load();

            // Replace the current content of the mainPane with the addEmployeView
            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    void NavFact(ActionEvent event) {
        try {
            // Load the addEmploye.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/AjoutFacture.fxml"));
            Parent addEmployeView = loader.load();

            // Replace the current content of the mainPane with the addEmployeView
            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    void NavPaie(ActionEvent event) {
        try {
            // Load the addEmploye.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/AjoutPaiement.fxml"));
            Parent addEmployeView = loader.load();

            // Replace the current content of the mainPane with the addEmployeView
            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void Retour(MouseEvent mouseEvent) {

    }
}
