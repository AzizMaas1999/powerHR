package tn.esprit.powerHR.test.PaiePointage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.PaiePointage.Paie;
import tn.esprit.powerHR.models.PaiePointage.Pointage;
import tn.esprit.powerHR.services.PaiePointage.ServiceApi;
import tn.esprit.powerHR.services.ServiceEmploye;
import tn.esprit.powerHR.services.PaiePointage.ServicePaie;
import tn.esprit.powerHR.services.PaiePointage.ServicePointage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/PaiePointage/PaieHome.fxml"));
        try {
            if (LocalDate.now().getDayOfMonth() == 1 && LocalTime.now().getHour() == 8 && LocalTime.now().getMinute() <= 30 ) {
                ServicePointage spoi = new ServicePointage();
                ServicePaie sp = new ServicePaie();
                List<Integer> IdsPaie = sp.getAll().stream()
                        .filter(p -> p.getMois().equals(LocalDate.now().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH))
                                && p.getAnnee().equals(String.valueOf(LocalDate.now().getYear())))
                        .filter(p -> p.getAnnee().equals(String.valueOf(LocalDate.now().getYear())))
                        .map(Paie::getId)
                        .toList();
                List<Employe> employes = new ArrayList<>();
                for (int id : IdsPaie) {
                    employes.add(spoi.getAll().stream()
                            .filter(p -> p.getPaie().getId() == id)
                            .map(Pointage::getEmploye)
                            .findFirst()
                            .get());
                }
                ServiceApi srvApi = new ServiceApi();
                srvApi.response(employes);
            }
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("powerHR");
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Icon.png")));
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}