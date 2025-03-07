package tn.esprit.powerHR.controllers.PaiePointage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
import java.util.stream.Collectors;

import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.PaiePointage.Paie;
import tn.esprit.powerHR.models.PaiePointage.Pointage; // Import your model
import tn.esprit.powerHR.services.PaiePointage.ServicePaie;
import tn.esprit.powerHR.services.PaiePointage.ServicePointage;
import tn.esprit.powerHR.services.User.ServiceEmploye;

public class AddPointageController {

    @FXML
    private Button bt_upload;

    @FXML
    private ImageView bt_back;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ListView<Pointage> lv_pointage;

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
            br.readLine();
            ServiceEmploye se = new ServiceEmploye();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
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
        if (pointageList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez importer un fichier CSV");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            List<Integer> emp = pointageList.stream()
                    .map(e -> e.getEmploye().getId())
                    .distinct()
                    .collect(Collectors.toList());
            System.out.println(pointageList);
            System.out.println(emp);

            ServicePaie spaie = new ServicePaie();
            ServiceEmploye se = new ServiceEmploye();
            ServicePointage sp = new ServicePointage();
            for (int i : emp) {
                long nbjr = pointageList.stream()
                        .map(e -> e.getEmploye().getId())
                        .filter(e -> e.equals(i))
                        .count();
                String mois = pointageList.get(0).getDate().toLocalDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

                String annee = String.valueOf(pointageList.get(0).getDate().toLocalDate().getYear());

                float Total = (float) (se.getById(i).getSalaire() / 30) * nbjr;

                Paie paie = new Paie(0, (int) nbjr, Total, mois, annee, null);
//                if (spaie.getAll().stream()
//                        .anyMatch(p -> p.getMois().equals(mois) && p.getAnnee().equals(annee))) {
//                    Alert alertE = new Alert(Alert.AlertType.ERROR);
//                    alertE.setTitle("Erreur");
//                    alertE.setHeaderText(null);
//                    alertE.setContentText("Pointage déjà ajouté");
//                    alertE.showAndWait();
//                    return;
//                }
                spaie.add(paie);
                for (Pointage pointage : pointageList) {
                    if (pointage.getEmploye().getId() == i) {
                        Paie p = spaie.getAll().get(spaie.getAll().size() - 1);
                        pointage.setPaie(p);
                    }
                }
            }

            for (Pointage pointage : pointageList) {
                sp.add(pointage);
            }
            alert.setTitle("Ajout de pointage");
            alert.setHeaderText(null);
            alert.setContentText("Ajout de pointage effectué avec succès");
            alert.showAndWait();

        }
    }

    @FXML
    void Retour(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PaiePointage/PaieHome.fxml"));
            Parent statView = loader.load();

            mainPane.getChildren().setAll(statView);
        } catch (IOException e) {
            System.err.println("Error loading " + e.getMessage());
        }
    }
}
