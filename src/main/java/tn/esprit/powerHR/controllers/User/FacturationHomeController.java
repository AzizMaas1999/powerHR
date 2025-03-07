package tn.esprit.powerHR.controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import tn.esprit.powerHR.models.User.Employe;

import java.io.IOException;

public class FacturationHomeController {

    @FXML
    private Button bt_article;

    @FXML
    private Button bt_clfr;

    @FXML
    private Button bt_facture;

    @FXML
    private Button bt_feedback;

    @FXML
    private Button bt_logout;

    @FXML
    private Button bt_paiement;

    @FXML
    private Label lb_logedInUser;

    @FXML
    private AnchorPane mainPane;

    private Employe loggedInUser;

    public Employe getLoggedInUser() {
         return loggedInUser;
    }

    public void setLoggedInUser(Employe loggedInUser) {
        this.loggedInUser = loggedInUser;
        lb_logedInUser.setText(loggedInUser.getUsername());
    }




    @FXML
    void Logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/LogIn.fxml"));
            Parent statView = loader.load();

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }

    @FXML
    void article(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/AjoutArticle.fxml"));
            Parent statView = loader.load();

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }

    @FXML
    void clfr(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClfrFeedback/ajouterCLFr.fxml"));
            Parent statView = loader.load();

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }

    @FXML
    void fact(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/AjoutFacture.fxml"));
            Parent statView = loader.load();

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }

    @FXML
    void feedback(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClfrFeedback/ajouterFeedback.fxml"));
            Parent statView = loader.load();

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }

    @FXML
    void paiement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/AjoutPaiement.fxml"));
            Parent statView = loader.load();

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }

}
