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
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import tn.esprit.powerHR.utils.EmojiUtils;
import tn.esprit.powerHR.services.ServiceApi;


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
    private Label lblSentiment; // Nouveau label pour afficher le type détecté


    private ServiceFeedback serviceFeedback = new ServiceFeedback();

    private final ServiceApi serviceApi = new ServiceApi(); // Service pour l'analyse du sentiment
    private Feedback p;
    public void setListFeedback(Feedback p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public Feedback getListFeedback() {
        return p;
    }


    /*@FXML
    public void initialize() {
        lv_ShowFeedback.setCellFactory(listView -> new FeedbackListCell());
        refreshList();

        // Ajout d'un listener pour analyser le texte et convertir les emojis
        Description.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                // ✅ Convertir les emojis AVANT l'analyse du sentiment
                String convertedText = EmojiUtils.replaceEmoticons(newValue);

                if (!convertedText.equals(newValue)) {
                    Description.setText(convertedText); // Évite une boucle infinie
                }

                // ✅ Détection automatique du sentiment
                String detectedSentiment = serviceApi.analyserSentiment(convertedText);
                lblSentiment.setText("Sentiment détecté: " + detectedSentiment);
            } else {
                lblSentiment.setText("Sentiment: En attente...");
            }
        });
    }*/


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
            // Détection automatique du sentiment
            String detectedSentiment = serviceApi.analyserSentiment(descriptionText);
            lblSentiment.setText("Sentiment détecté: " + detectedSentiment);

            /*if (type.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Type manquant", "Veuillez sélectionner un type de feedback.");
                return;
            }*/

            Feedback feedback = new Feedback();
            feedback.setDateCreation(Date.valueOf(Date_creation.getValue()));
            feedback.setDescription(descriptionText);
            feedback.setType(detectedSentiment); // Affectation du type détecté

            // feedback.setType(type.getValue());

            CLFr clfr = new CLFr();
            clfr.setId(1);
            feedback.setClfr(clfr);

            serviceFeedback.add(feedback);
            System.out.println("Feedback ajouté avec succès !");

            refreshList();
            showAlert(Alert.AlertType.INFORMATION, "Ajout réussi", "Le feedback a été ajouté avec succès.");

            Date_creation.setValue(null);
            Description.clear();
            lblSentiment.setText("Sentiment: En attente...");
          //  type.setValue(null);

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
    public void initialize() {
        lv_ShowFeedback.setCellFactory(listView -> new FeedbackListCell());
        refreshList();

        // Ajout d'un listener pour analyser le texte et convertir les emojis
        Description.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                // ✅ Convertir les emojis AVANT l'analyse du sentiment
                String convertedText = EmojiUtils.replaceEmoticons(newValue);

                if (!convertedText.equals(newValue)) {
                    Description.setText(convertedText); // Évite une boucle infinie
                }

                // ✅ Détection automatique du sentiment
                String detectedSentiment = serviceApi.analyserSentiment(convertedText);
                lblSentiment.setText("Sentiment détecté: " + detectedSentiment);
            } else {
                lblSentiment.setText("Sentiment: En attente...");
            }
        });
    }

    @FXML
    void Supp(ActionEvent event) {
        Feedback selectedFeedback = lv_ShowFeedback.getSelectionModel().getSelectedItem();
        if (selectedFeedback == null) {
            showAlert(Alert.AlertType.WARNING, "Suppression impossible", "Veuillez sélectionner un feedback à supprimer.");
            return;
        }

        try {
            serviceFeedback.delete(selectedFeedback);
            refreshList();
            showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", "Le feedback a été supprimé avec succès.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression : " + e.getMessage());
        }
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void analyserSentiment(ActionEvent event) {
        String texte = Description.getText();

        if (texte.isEmpty()) {
            lblSentiment.setText("Sentiment: Veuillez entrer un texte.");
            return;
        }

        String sentiment = serviceApi.analyserSentiment(texte);
        lblSentiment.setText("Sentiment: " + sentiment);
    }

}

