package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.models.Pointage;
import tn.esprit.powerHR.services.ServicePaie;
import tn.esprit.powerHR.services.ServicePointage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FichePaieController {

    @FXML
    private Button bt_download;

    @FXML
    private ListView<Paie> lv_paie;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField tf_search_paie;

    ObservableList<Paie> observableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            ServicePaie sp = new ServicePaie();
            ServicePointage spointage = new ServicePointage();
            List<Integer> listIdPointage = spointage.getAll().stream()
                    .filter(p -> p.getEmploye().getId() == 1) //supposons aziz d'id 1 est connecté
                    .map(e->e.getPaie().getId())
                    .distinct()
                    .toList();
            List<Paie> paies = new ArrayList<>();
            for (Integer i : listIdPointage) {
                paies.add(sp.getAll().stream()
                        .filter(p -> p.getId() == i)
                        .findFirst()
                        .get());
            }

            observableList = FXCollections.observableArrayList(paies);

            lv_paie.setItems(observableList);

            lv_paie.setCellFactory(param -> new ListCell<Paie>() {
                @Override
                protected void updateItem(Paie paie, boolean empty) {
                    super.updateItem(paie, empty);

                    if (empty || paie == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox();
                        hbox.setSpacing(10);

                        Text nbrjr = new Text(String.valueOf(paie.getNbjour()));
                        Text total = new Text(String.valueOf(paie.getMontant()));
                        Text mois = new Text(paie.getMois());
                        Text annee = new Text(paie.getAnnee());

                        nbrjr.setWrappingWidth(200);
                        total.setWrappingWidth(135);
                        mois.setWrappingWidth(55);
                        annee.setWrappingWidth(40);

                        Button pointageButton = new Button();
                        pointageButton.setStyle("-fx-background-color: #eeeeee; -fx-cursor: hand;");
                        pointageButton.setText("Afficher Pointage");

                        pointageButton.setOnAction(event -> {
                            try {

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PointageEmp.fxml"));
                                Parent statView = loader.load();
                                PointageEmpController controller = loader.getController();
                                System.out.println("ID1: " + paie.getId());
                                controller.setLv_pointage(paie.getId());

                                mainPane.getChildren().setAll(statView);
                            } catch (IOException e) {
                                System.err.println("Error loading " + e.getMessage());
                            }
                        });
                        hbox.getChildren().addAll(nbrjr, total, mois, annee, pointageButton);
                        setGraphic(hbox);
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    @FXML
    void download(ActionEvent event) {

    }

    @FXML
    void search(KeyEvent event) {

    }

}