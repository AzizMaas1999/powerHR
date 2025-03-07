package tn.esprit.powerHR.controllers.ClfrFeedback;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.ClfrFeedback.CLFr;
import tn.esprit.powerHR.models.ClfrFeedback.Feedback;
import tn.esprit.powerHR.services.ClfrFeedback.ServiceFeedback;
import tn.esprit.powerHR.utils.ClfrFeedback.EmojiUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AjouterFeedback {

    @FXML
    private ListView<Feedback> lv_ShowFeedback;
    @FXML
    private DatePicker Date_creation;
    @FXML
    private TextField Description;
    @FXML
    private Button bt_ajouterfeedback, bt_modiferfeedback, bt_submit, bt_supppfeedback;
    @FXML
    private Label lblSentiment;
    @FXML
    private ComboBox<String> feedbackTypeComboBox;

    private final ServiceFeedback serviceFeedback = new ServiceFeedback();

    public void refreshListView() {
        try {
            List<Feedback> feedbacks = serviceFeedback.getAll();
            ObservableList<Feedback> observableList = FXCollections.observableArrayList(feedbacks);
            lv_ShowFeedback.setItems(observableList);
        } catch (Exception e) {
            System.out.println("Erreur rafraîchissement: " + e.getMessage());
        }
    }

    @FXML
    void AjouterFeedBack(ActionEvent event) {
        try {
            // Validation de la date
            if (Date_creation.getValue() == null || Date_creation.getValue().isAfter(LocalDate.now())) {
                showAlert(Alert.AlertType.WARNING, "Date invalide", "Veuillez sélectionner une date valide (passée ou aujourd'hui).");
                return;
            }

            // Validation de la description
            String descriptionText = Description.getText().trim();
            if (descriptionText.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Description manquante", "La description ne peut pas être vide.");
                return;
            } else if (descriptionText.length() < 10) {
                showAlert(Alert.AlertType.WARNING, "Description trop courte", "La description doit contenir au moins 10 caractères.");
                return;
            }

            // Validation du type
            if (feedbackTypeComboBox.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Type manquant", "Veuillez sélectionner un type.");
                return;
            }

            // Création du feedback
            Feedback feedback = new Feedback();

            // Combiner la date choisie et l'heure actuelle
            LocalDate selectedDate = Date_creation.getValue();
            LocalTime currentTime = LocalTime.now();
            LocalDateTime dateTime = LocalDateTime.of(selectedDate, currentTime);
            feedback.setDateCreation(Timestamp.valueOf(dateTime));

            feedback.setDescription(descriptionText);
            feedback.setType(feedbackTypeComboBox.getValue());

            // Simulation d'un CLFr (à adapter selon votre logique métier)
            CLFr clfr = new CLFr();
            clfr.setId(1); // ID temporaire – à remplacer par l'ID réel de l'utilisateur connecté
            feedback.setClfr(clfr);

            // Ajout en base
            serviceFeedback.add(feedback);
            refreshListView();

            // Réinitialisation du formulaire
            Date_creation.setValue(LocalDate.now());
            Description.clear();
            feedbackTypeComboBox.setValue(null);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Feedback ajouté avec succès !");
        } catch (Exception e) {
            System.err.println("Erreur d'ajout : " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout : " + e.getMessage());
        }
    }

    private String getEmoji(String sentiment) {
        return switch(sentiment) {
            case "Positif" -> " 😊";
            case "Négatif" -> " 😠";
            case "Neutre" -> " 😐";
            default -> "";
        };
    }

    @FXML
    private void navigateModifier(ActionEvent event) {
        Feedback selected = lv_ShowFeedback.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un feedback à modifier.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClfrFeedback/modifierFeedback.fxml"));
            Parent root = loader.load();

            UpdateFeedback controller = loader.getController();
            controller.initData(selected, this);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de navigation", "Une erreur est survenue lors de la navigation.");
        }
    }

    @FXML
    public void initialize() {
        // Initialisation du DatePicker sur la date d'aujourd'hui
        Date_creation.setValue(LocalDate.now());
        // Définir la cellule personnalisée pour la ListView
        lv_ShowFeedback.setCellFactory(param -> new FeedbackListCell());
        refreshListView();

        // Initialiser les types de feedback
        feedbackTypeComboBox.setItems(FXCollections.observableArrayList(
                "Non analysé",
                "Positif",
                "Négatif",
                "Neutre"
        ));

        // Conversion des émoticônes en emojis dans le champ Description
        Description.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                String convertedText = EmojiUtils.replaceEmoticons(newValue);
                if (!convertedText.equals(newValue)) {
                    Description.setText(convertedText);
                }
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce feedback ?");
        alert.setContentText("Cette action est irréversible.");
        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                serviceFeedback.delete(selectedFeedback);
                refreshListView();
                showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", "Le feedback a été supprimé avec succès.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression : " + e.getMessage());
            }
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
