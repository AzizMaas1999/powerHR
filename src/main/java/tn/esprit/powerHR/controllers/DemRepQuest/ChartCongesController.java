package tn.esprit.powerHR.controllers.DemRepQuest;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import tn.esprit.powerHR.models.DemRepQuest.Demande;
import tn.esprit.powerHR.services.DemRepQuest.DemandeService;

import java.io.IOException;
import java.util.List;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class ChartCongesController {

    @FXML
    private BarChart<String, Number> chart_conges;

    @FXML
    private AnchorPane mainPane;


    private DemandeService ds = new DemandeService();

    @FXML
    public void initialize() {
        loadCongesChart();
    }

    private void loadCongesChart() {
        List<Demande> demandes = ds.getAll();

        int[] congesParMois = new int[12];

        for (Demande d : demandes) {
            if ("Conges".equals(d.getType()) && d.getDateDebut() != null) {
                LocalDate dateDebut = d.getDateDebut().toLocalDate();
                int moisIndex = dateDebut.getMonthValue() - 1;
                congesParMois[moisIndex]++;
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Congés par mois");

        for (int i = 0; i < 12; i++) {
            String moisNom = Month.of(i + 1).getDisplayName(TextStyle.FULL, Locale.FRENCH);
            series.getData().add(new XYChart.Data<>(moisNom, congesParMois[i]));
        }

        chart_conges.getData().clear();
        chart_conges.getData().add(series);
    }

    @FXML

    void Retour(MouseEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/ListeDR.fxml"));
            Parent chart = loader.load();


            mainPane.getChildren().setAll(chart);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
}}
