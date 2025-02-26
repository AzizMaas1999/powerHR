package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.models.Pointage; // Import your model
import tn.esprit.powerHR.services.ServicePaie;
import tn.esprit.powerHR.services.ServicePointage;

public class AddPointageController {

    @FXML
    private Button bt_upload;

    @FXML
    private ListView<Pointage> lv_pointage; // Store Pointage objects

    @FXML
    private Button bt_submit;

    private ObservableList<Pointage> pointageList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        lv_pointage.setItems(pointageList);
    }

    @FXML
    private void handleUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            loadCSVData(file);
        }
    }

    private void loadCSVData(File file) {
        pointageList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Assuming CSV format: date,heureEntree,heureSortie,employeId
                if (parts.length == 4) {
                    String date = parts[0];
                    String heureEntree = parts[1];
                    String heureSortie = parts[2];
                    Employe employe = new Employe(Integer.parseInt(parts[3]),"Aziz","aziz","DirecteurRH",2000.0,"123456789123","123456789");

                    Pointage pointage = new Pointage( 0, Date.valueOf(date), Time.valueOf(heureEntree), Time.valueOf(heureSortie), employe, null);
                    pointageList.add(pointage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
    }
}

    @FXML
    private void AjouterPointage() {
//        List<Integer> emp = pointageList.stream()
//                .map(Pointage::getId)
//                .distinct()
//                .collect(Collectors.toList());
//        ServicePaie spaie = new ServicePaie();
//        for (int i : emp) {
//            long nbjr = pointageList.stream()
//                    .map(Pointage::getId)
//                    .filter(e->e.equals(i))
//                    .count();
//            String mois = pointageList.getFirst().getDate().toLocalDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
//
//            long To

//        }
//        ServicePointage spoin = new ServicePointage();
//        for (Pointage pointage : pointageList) {
//            spoin.add(pointage);
//        }

}
}
