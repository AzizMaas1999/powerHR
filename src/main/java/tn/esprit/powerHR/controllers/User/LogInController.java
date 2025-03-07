package tn.esprit.powerHR.controllers.User;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.User.*;
import tn.esprit.powerHR.services.User.ServiceEmploye;

import java.io.IOException;
import java.util.List;

public class LogInController {

    @FXML
    private Button bt_login;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_username;

    @FXML
    void login(ActionEvent event) {
        ServiceEmploye se = new ServiceEmploye();
        List<Employe> employes = se.getAll();
        Alert alertE = new Alert(Alert.AlertType.ERROR);
        Alert alertI = new Alert(Alert.AlertType.INFORMATION);


        Argon2 argon2 = Argon2Factory.create();


        if (tf_username.getText().isEmpty() || tf_password.getText().isEmpty()) {
            alertE.setTitle("Login Error");
            alertE.setHeaderText(null);
            alertE.setContentText("Veuillez remplir tous les champs !");
            alertE.showAndWait();
        } else {
            for (Employe employe : employes) {
                System.out.println(employe.getUsername());
                if (employe.getUsername().equals(tf_username.getText())) {
                    if (argon2.verify(employe.getPassword(), tf_password.getText())) {
                        alertI.setTitle("Login Success");
                        alertI.setHeaderText(null);
                        alertI.setContentText("Bienvenue " + employe.getUsername());
                        alertI.showAndWait();
                        if (employe.getPoste().equals(Poste.Directeur)) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/DirecteurHome.fxml"));
                                Parent statView = loader.load();

                                DirecteurHomeController controller = loader.getController();
                                controller.setLoggedInUser(employe);

                                mainPane.getChildren().setAll(statView);
                            } catch (IOException e) {
                                System.err.println("Error loading " + e.getMessage());
                            }
                            return;
                        } else if (employe.getPoste().equals(Poste.Charges)) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ChargesHome.fxml"));
                                Parent statView = loader.load();

                                ChargesHomeController controller = loader.getController();
                                controller.setLoggedInUser(employe);

                                mainPane.getChildren().setAll(statView);
                            } catch (IOException e) {
                                System.err.println("Error loading " + e.getMessage());
                            }
                            return;
                        } else if (employe.getPoste().equals(Poste.Ouvrier)) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/OuvrierHome.fxml"));
                                Parent statView = loader.load();

                                OuvrierHomeController controller = loader.getController();
                                controller.setLoggedInUser(employe);
                                System.out.println(employe.getUsername());

                                mainPane.getChildren().setAll(statView);
                            } catch (IOException e) {
                                System.err.println("Error loading " + e.getMessage());
                            }
                            return;
                        } else if (employe.getPoste().equals(Poste.Facturation)) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FacturationHome.fxml"));
                                Parent statView = loader.load();

                                FacturationHomeController controller = loader.getController();
                                controller.setLoggedInUser(employe);

                                mainPane.getChildren().setAll(statView);
                            } catch (IOException e) {
                                System.err.println("Error loading " + e.getMessage());
                            }
                            return;
                        } else if (employe.getPoste().equals(Poste.Admin)) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("EntrepriseDep/fxml/EntrepriseView.fxml"));
                                Parent statView = loader.load();

                                mainPane.getChildren().setAll(statView);
                            } catch (IOException e) {
                                System.err.println("Error loading " + e.getMessage());
                            }
                            return;
                        }
                    }
                } else if (employes.indexOf(employe) == employes.size() - 1) {
                    alertE.setTitle("Login Error");
                    alertE.setHeaderText(null);
                    alertE.setContentText("Veuillez vérifier vos informations !");
                    alertE.showAndWait();
                }
            }

        }
    }
}