package tn.esprit.powerHR.controllers.PaiePointage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tn.esprit.powerHR.models.PaiePointage.Pointage;
import tn.esprit.powerHR.services.PaiePointage.ServicePaie;
import tn.esprit.powerHR.services.PaiePointage.ServicePointage;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class StatPaieController {

    @FXML
    private LineChart<String, Double> c_retardParMois;

    @FXML
    private ImageView bt_back;

    @FXML
    private Tab tab_RetardParMois;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private BarChart<String, Double> c_retardParSalaire;

    @FXML
    private Tab tab_retardParSalaire;

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

    @FXML
    void initialize() {
        RetardParMois();
        RetardParSalaire();
    }

    void RetardParMois() {
        try {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName("Moyenne De Retard Par Mois Pour " + (LocalDate.now().getYear()-1));
            ServicePaie sp = new ServicePaie();
            ServicePointage spointage = new ServicePointage();
            List<String> monthOrder = List.of(
                    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
            );
            List<String> mois = sp.getAll().stream()
                    .map(p -> p.getMois())
                    .distinct()
                    .sorted(Comparator.comparingInt(monthOrder::indexOf))
                    .toList();
            for (String m : mois) {
                List<Time> time = spointage.getAll().stream()
                        .filter(p -> p.getPaie().getMois().equals(m))
                        .filter(p -> p.getPaie().getAnnee().equals(String.valueOf((LocalDate.now().getYear()-1))))
                        .map(Pointage::getHeureEntree)
                        .toList();
                int TotalMin = 0;
                for (Time t : time) {
                    TotalMin += t.toLocalTime().getMinute();
                }
                double avgTime = time.isEmpty() ? 0 : (double) TotalMin / time.size();
                series.getData().add(new XYChart.Data<>(m,avgTime));
            }
            c_retardParMois.getData().add(series);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void RetardParSalaire() {
        try {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName("Moyenne De Retard Par Salaire");
            ServicePaie sp = new ServicePaie();
            ServicePointage spointage = new ServicePointage();
            List<Time> timeLow = spointage.getAll().stream()
                    .filter(p -> p.getEmploye().getSalaire() < 1000)
                    .map(Pointage::getHeureEntree)
                    .toList();
            List<Time> timeMedium = spointage.getAll().stream()
                    .filter(p -> p.getEmploye().getSalaire() >= 1000 && p.getEmploye().getSalaire() < 2000)
                    .map(Pointage::getHeureEntree)
                    .toList();
            List<Time> timeHigh = spointage.getAll().stream()
                    .filter(p -> p.getEmploye().getSalaire() >= 2000)
                    .map(Pointage::getHeureEntree)
                    .toList();
            int TotalMinLow = 0;
            int TotalMinMedium = 0;
            int TotalMinHigh = 0;
            for (Time t : timeLow) {
                TotalMinLow += t.toLocalTime().getMinute();
            }
            for (Time t : timeMedium) {
                TotalMinMedium += t.toLocalTime().getMinute();
            }
            for (Time t : timeHigh) {
                TotalMinHigh += t.toLocalTime().getMinute();
            }
            double avgTimeLow = timeLow.isEmpty() ? 0 : (double) TotalMinLow / timeLow.size();
            double avgTimeMedium = timeMedium.isEmpty() ? 0 : (double) TotalMinMedium / timeMedium.size();
            double avgTimeHigh = timeHigh.isEmpty() ? 0 : (double) TotalMinHigh / timeHigh.size();
            series.getData().add(new XYChart.Data<>("[..1000]", avgTimeLow));
            series.getData().add(new XYChart.Data<>("[1000..2000]", avgTimeMedium));
            series.getData().add(new XYChart.Data<>("[2000..]", avgTimeHigh));
            c_retardParSalaire.getData().add(series);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
