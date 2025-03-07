package tn.esprit.powerHR.controllers.DemRepQuest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tn.esprit.powerHR.controllers.User.OuvrierHomeController;
import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.services.DemRepQuest.HolidayAPI;
import tn.esprit.powerHR.models.DemRepQuest.Holiday;
import javafx.collections.FXCollections;

import java.io.IOException;

public class HolidayController {

    @FXML
    private TableView<Holiday> holidaysTable;

    @FXML
    private TableColumn<Holiday, String> nameColumn;

    @FXML
    private TableColumn<Holiday, String> dateColumn;

    @FXML
    private AnchorPane mainPane;

    private Employe loggedInUser;

    public Employe getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Employe loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadHolidays();
    }

    private void loadHolidays() {
        holidaysTable.setItems(FXCollections.observableArrayList(new HolidayAPI().getHolidays()));
    }

    public void Retour(MouseEvent mouseEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/AjoutD.fxml"));
            Parent addEmployeView = loader.load();

            AjoutController ajoutController = loader.getController();
            ajoutController.setLoggedInUser(getLoggedInUser());

            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
