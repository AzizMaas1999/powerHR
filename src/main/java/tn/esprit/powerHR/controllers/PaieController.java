package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.models.Pointage;
import tn.esprit.powerHR.services.ServicePaie;
import tn.esprit.powerHR.services.ServicePointage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaieController {

    @FXML
    private Button btn_modifier_paie;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button btn_stat_paie;

    @FXML
    private TextField tf_search_paie;

    @FXML
    private ListView<String> lv_paie;

    ObservableList<String> observableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ServicePaie sp = new ServicePaie();
        ServicePointage spointage = new ServicePointage();
        if (tf_search_paie.getText().isEmpty()) {
            try {
                List<Paie> listPaie = sp.getAll();
                List<Pointage> listPointage = spointage.getAll();
                List<String> listShow = new ArrayList<>();
                for (Paie p : listPaie) {
                    Employe user = listPointage.stream()
                            .filter(t -> t.getPaie().getId() == p.getId())
                            .map(Pointage::getEmploye)
                            .findFirst()
                            .get();
                    listShow.add(
                            user.getUsername()
                                    + "                                                      " +
                                    p.getNbjour()
                                    + "                                             " +
                                    p.getMontant()
                                    + "                    " +
                                    p.getMois()
                                    + "               " +
                                    p.getAnnee());
                }
                    observableList.addAll(listShow);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        lv_paie.setItems(observableList);
    }

    @FXML
    void search(KeyEvent event) {
        System.out.println("search");
        lv_paie.getItems().clear();
        ServicePaie sp = new ServicePaie();
        ServicePointage spointage = new ServicePointage();
        try {
            List<Paie> listPaie = sp.getAll();
            List<Pointage> listPointage = spointage.getAll();
            List<String> listShow = new ArrayList<>();
            for (Paie p : listPaie) {
                Employe user = listPointage.stream()
                        .filter(t -> t.getPaie().getId() == p.getId())
                        .map(Pointage::getEmploye)
                        .findFirst()
                        .get();
                if (user.getUsername().toLowerCase().contains(tf_search_paie.getText().toLowerCase())){
                listShow.add(
                        user.getUsername()
                                + "                                                      " +
                                p.getNbjour()
                                + "                                             " +
                                p.getMontant()
                                + "                    " +
                                p.getMois()
                                + "               " +
                                p.getAnnee());
            }
            }
            observableList.addAll(listShow);


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void Mod(ActionEvent event) {

    }

    @FXML
    void Stat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StatPaie.fxml"));
            Parent statView = loader.load();

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }

    }
}
