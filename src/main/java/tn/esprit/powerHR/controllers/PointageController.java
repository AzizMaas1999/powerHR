package tn.esprit.powerHR.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import tn.esprit.powerHR.models.Pointage;

import java.sql.Date;
import java.sql.Time;

public class PointageController {

    @FXML
    private TableView<?> tb_pointage;

    @FXML
    private TextField tf_date;

    @FXML
    private TextField tf_heureEntree;

    @FXML
    private TextField tf_heureSortie;

    @FXML
    void AjouterPointage(ActionEvent event) {
        Pointage p = new Pointage();
        p.setDate(Date.valueOf(tf_date.getText()));
        p.setHeureEntree(Time.valueOf(tf_heureEntree.getText()));
        p.setHeureSortie(Time.valueOf(tf_heureSortie.getText()));
        System.out.println(p);

    }

}
