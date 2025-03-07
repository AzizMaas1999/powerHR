package tn.esprit.powerHR.controllers.DemRepQuest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Charger et afficher les jours fériés
        loadHolidays();
    }

    private void loadHolidays() {
        // Récupérer les jours fériés depuis l'API et les ajouter à la TableView
        holidaysTable.setItems(FXCollections.observableArrayList(new HolidayAPI().getHolidays()));
    }

    public void Retour(MouseEvent mouseEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/AjoutD.fxml"));
            Parent addEmployeView = loader.load();


            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
