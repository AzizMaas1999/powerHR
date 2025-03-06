package tn.esprit.powerHR.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import tn.esprit.powerHR.models.Demande;
import tn.esprit.powerHR.services.DemandeService;

import java.io.IOException;
import java.util.List;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class ChartCongesController {

    @FXML
    private BarChart<String, Number> chart_conges;


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
                int moisIndex = dateDebut.getMonthValue() - 1; // Mois de 0 à 11
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
    void retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutD.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) chart_conges.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.err.println("Erreur de chargement : " + e.getMessage());
        }
    }
}
