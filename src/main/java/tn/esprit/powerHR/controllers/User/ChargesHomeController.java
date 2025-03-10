package tn.esprit.powerHR.controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import tn.esprit.powerHR.controllers.DemRepQuest.AjoutQ;
import tn.esprit.powerHR.models.User.Employe;

import java.io.IOException;

public class ChargesHomeController {

    @FXML
    private Button bt_emp;

    @FXML
    private Button bt_logout;

    @FXML
    private Button bt_paie;

    @FXML
    private Button bt_questionnaire;

    @FXML
    private Label lb_logedInUser;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button bt_fiche;

    private Employe loggedInUser;

    public Employe getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Employe loggedInUser) {
        this.loggedInUser = loggedInUser;
        lb_logedInUser.setText(loggedInUser.getUsername());
    }

    @FXML
    void emp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ManageEmploye.fxml"));
            Parent statView = loader.load();

            ManageEmployeController controller = loader.getController();
            controller.setLoggedInUser(getLoggedInUser());

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }

    @FXML
    void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/LogIn.fxml"));
            Parent statView = loader.load();

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }

    @FXML
    void paie(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PaiePointage/PaieHome.fxml"));
            Parent statView = loader.load();

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }

    @FXML
    void quest(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/AjoutQ.fxml"));
            Parent statView = loader.load();

            AjoutQ controller = loader.getController();
            controller.setLoggedInUser(getLoggedInUser());

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }

    @FXML
    void fiche(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ManageFiche.fxml"));
            Parent statView = loader.load();

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }
    }

}
