package tn.esprit.powerHR.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.powerHR.services.HolidayAPI;
import tn.esprit.powerHR.models.Holiday;
public class HolidayController {

    @FXML
    private TableView<Holiday> holidaysTable;

    @FXML
    private TableColumn<Holiday, String> nameColumn;

    @FXML
    private TableColumn<Holiday, String> dateColumn;


    private ObservableList<Holiday> holidaysList = FXCollections.observableArrayList();

    public void initialize() {
        // Initialisation des colonnes
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Charger les jours fériés au démarrage
        loadHolidays();
    }


    private void loadHolidays() {


        HolidayAPI holidayAPI = new HolidayAPI();
        holidayAPI.getHolidays().forEach(holiday -> {
            holidaysList.add(new Holiday(holiday.getName(), holiday.getDate()));
        });

        holidaysTable.setItems(holidaysList); // Afficher les données dans la TableView
    }
}
