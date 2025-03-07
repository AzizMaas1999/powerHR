package tn.esprit.powerHR.controllers.PaiePointage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import tn.esprit.powerHR.models.PaiePointage.Pointage;
import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.services.PaiePointage.ServicePointage;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class PointageEmpController {

    @FXML
    private ListView<Pointage> lv_pointage;

    @FXML
    private ImageView bt_back;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private DatePicker tf_search_pointage;

    ObservableList<Pointage> observableList = FXCollections.observableArrayList();

    private Employe loggedInUser;

    public Employe getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Employe loggedInUser) {
        this.loggedInUser = loggedInUser;
        initialize();
    }

    @FXML
    public void initialize() {
        lv_pointage.setItems(observableList);
    }

    public void setLv_pointage(int id) {
        try {
                ServicePointage spointage = new ServicePointage();
                List<Pointage> pointages = spointage.getAll().stream()
                        .filter(p -> p.getPaie().getId() == id)
                        .toList();

                observableList = FXCollections.observableArrayList(pointages);

                lv_pointage.setItems(observableList);

                lv_pointage.setCellFactory(param -> new ListCell<Pointage>() {
                    @Override
                    protected void updateItem(Pointage pointage, boolean empty) {
                        super.updateItem(pointage, empty);

                        if (empty || pointage == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox();
                            hbox.setSpacing(10);

                            Text date = new Text(String.valueOf(pointage.getDate()));
                            Text hEnt = new Text(String.valueOf(pointage.getHeureEntree()));
                            Text hSor = new Text(String.valueOf(pointage.getHeureSortie()));

                            date.setWrappingWidth(200);
                            hEnt.setWrappingWidth(135);
                            hSor.setWrappingWidth(55);

                            hbox.getChildren().addAll(date, hEnt, hSor);
                            setGraphic(hbox);
                        }
                    }
                });
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
    }

    @FXML
    void Retour(MouseEvent event) {
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
    void search(ActionEvent event) {
        try {
//            System.out.println(tf_search_pointage.getValue());
            List<Pointage> pointages = observableList.stream()
                    .filter(p -> p.getDate().equals(Date.valueOf(tf_search_pointage.getValue())))
                    .toList();
//            System.out.println(pointages);

            ObservableList<Pointage> observableList1 = FXCollections.observableArrayList(pointages);

            lv_pointage.setItems(observableList1);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
