package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
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

import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.models.Pointage; // Import your model
import tn.esprit.powerHR.services.ServiceEmploye;
import tn.esprit.powerHR.services.ServicePaie;
import tn.esprit.powerHR.services.ServicePointage;

public class AddPointageController {

    @FXML
    private Button bt_upload;

    @FXML
    private AnchorPane mainPane;

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
        File downloadsDir = new File(System.getProperty("user.home"), "Downloads");
        if (downloadsDir.exists()) {
            fileChooser.setInitialDirectory(downloadsDir);
        }
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
            ServiceEmploye se = new ServiceEmploye();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Assuming CSV format: date,heureEntree,heureSortie,employeId
                if (parts.length == 4) {
                    String date = parts[0];
                    String heureEntree = parts[1];
                    String heureSortie = parts[2];
//                    Employe employe = new Employe(Integer.parseInt(parts[3]),"Aziz","aziz", Poste.Charges,2000.0,"123456789123","123456789",null,null,null,null,null);

                    Pointage pointage = new Pointage( 0, Date.valueOf(date), Time.valueOf(heureEntree), Time.valueOf(heureSortie), se.getById(Integer.parseInt(parts[3])), null);
                    pointageList.add(pointage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
    }
}

    @FXML
    private void AjouterPointage() {
        List<Integer> emp = pointageList.stream()
                .map(e->e.getEmploye().getId())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(pointageList);
        System.out.println(emp);

        ServicePaie spaie = new ServicePaie();
        ServiceEmploye se = new ServiceEmploye();
        for (int i : emp) {
            long nbjr = pointageList.stream()
                    .map(e->e.getEmploye().getId())
                    .filter(e->e.equals(i))
                    .count();
            String mois = pointageList.get(0).getDate().toLocalDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            String annee = String.valueOf(pointageList.get(0).getDate().toLocalDate().getYear());

            float Total =(float) (se.getById(i).getSalaire() / 30) * nbjr;

            Paie paie = new Paie(0, (int) nbjr, Total, mois, annee, null);
            spaie.add(paie);
            for (Pointage pointage : pointageList) {
                if (pointage.getEmploye().getId() == i) {
                    Paie p = spaie.getAll().get(spaie.getAll().size() - 1);
                    pointage.setPaie(p);
                }
            }
        }

            ServicePointage spoin = new ServicePointage();
        for (Pointage pointage : pointageList) {
            spoin.add(pointage);
        }

    }
}
