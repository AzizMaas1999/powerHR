package tn.esprit.powerHR.controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import tn.esprit.powerHR.controllers.DemRepQuest.AjoutController;
import tn.esprit.powerHR.controllers.PaiePointage.FichePaieController;
import tn.esprit.powerHR.models.User.Employe;

import java.io.IOException;

public class OuvrierHomeController {

    @FXML
    private Button bt_dem;

    @FXML
    private Button bt_logout;

    @FXML
    private Button bt_paie;

    @FXML
    private Button bt_quest;

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
    void dem(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/AjoutD.fxml"));
            Parent statView = loader.load();

            AjoutController demandeController = loader.getController();
            demandeController.setLoggedInUser(loggedInUser);

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }

    @FXML
    void paie(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PaiePointage/FichePaie.fxml"));
            Parent statView = loader.load();

            FichePaieController fichePaieController = loader.getController();
            fichePaieController.setLoggedInUser(getLoggedInUser());

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }

    @FXML
    void quest(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/listequest.fxml"));
            Parent statView = loader.load();

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }

}
