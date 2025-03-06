package tn.esprit.powerHR.controllers.DemRepQuest;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.powerHR.services.DemRepQuest.HolidayAPI;
import tn.esprit.powerHR.models.DemRepQuest.Holiday;
import javafx.collections.FXCollections;

public class HolidayController {

    @FXML
    private TableView<Holiday> holidaysTable;

    @FXML
    private TableColumn<Holiday, String> nameColumn;

    @FXML
    private TableColumn<Holiday, String> dateColumn;

    public void initialize() {
        // Associer les colonnes aux attributs de l'objet Holiday
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Charger et afficher les jours fériés
        loadHolidays();
    }

    private void loadHolidays() {
        // Récupérer les jours fériés depuis l'API et les ajouter à la TableView
        holidaysTable.setItems(FXCollections.observableArrayList(new HolidayAPI().getHolidays()));
    }
}
