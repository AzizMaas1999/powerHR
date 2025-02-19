package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Feedback;
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.services.ServiceCLFr;
import tn.esprit.powerHR.services.ServiceFeedback;
import java.sql.Date;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AjouterFeedback {

    @FXML
    private DatePicker Date_creation;

    @FXML
    private TextField Description;

    @FXML
    private Button bt_ajouterfeedback;

    @FXML
    private Button bt_modiferfeedback;

    @FXML
    private Button bt_submit;

    @FXML
    private ListView<Feedback> lv_ShowFeedback;

    @FXML
    private Button bt_supppfeedback;

    @FXML
    private ChoiceBox<String> type;

    private ServiceFeedback serviceFeedback = new ServiceFeedback();

    private Feedback p;
    public void setListFeedback(Feedback p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public Feedback getListFeedback() {
        return p;
    }


    @FXML
    public void initialize() {
        type.getItems().addAll("Positif", "Négatif", "Constructif", "360°");
        refreshList();
    }

    private void refreshList() {
        try {
            List<Feedback> list = serviceFeedback.getAll();
            ObservableList<Feedback> observableList = FXCollections.observableList(list);
            lv_ShowFeedback.setItems(observableList);
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des feedbacks : " + e.getMessage());
        }
    }

    @FXML
    void AjouterFeedBack(ActionEvent event) {
        try {
            if (Date_creation.getValue() == null || Date_creation.getValue().isAfter(java.time.LocalDate.now())) {
                showAlert(Alert.AlertType.WARNING, "Date invalide", "Veuillez entrer une date valide (dans le passé ou aujourd'hui).");
                return;
            }

            String descriptionText = Description.getText().trim();
            if (descriptionText.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Description manquante", "La description ne peut pas être vide.");
                return;
            } else if (descriptionText.length() < 10) {
                showAlert(Alert.AlertType.WARNING, "Description trop courte", "La description doit contenir au moins 10 caractères.");
                return;
            }

            if (type.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Type manquant", "Veuillez sélectionner un type de feedback.");
                return;
            }

            Feedback feedback = new Feedback();
            feedback.setDateCreation(Date.valueOf(Date_creation.getValue()));
            feedback.setDescription(descriptionText);
            feedback.setType(type.getValue());

            CLFr clfr = new CLFr();
            clfr.setId(1);
            feedback.setClfr(clfr);

            serviceFeedback.add(feedback);
            System.out.println("Feedback ajouté avec succès !");

            refreshList();
            showAlert(Alert.AlertType.INFORMATION, "Ajout réussi", "Le feedback a été ajouté avec succès.");

            Date_creation.setValue(null);
            Description.clear();
            type.setValue(null);

        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de l'ajout du feedback.");
        }
    }

    @FXML
    void NavigateModif(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/modifierFeedback.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier Feedback");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    @FXML
    void Supp(ActionEvent event) {
        setListFeedback(lv_ShowFeedback.getSelectionModel().getSelectedItem());
        ServiceFeedback ps = new ServiceFeedback();
        try {
            ps.delete(getListFeedback());
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
