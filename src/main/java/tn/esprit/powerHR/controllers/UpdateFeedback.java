package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Feedback;
import tn.esprit.powerHR.services.ServiceFeedback;
import tn.esprit.powerHR.utils.EmojiUtils;
import javafx.event.ActionEvent;
import java.time.LocalDate;

public class UpdateFeedback {

    @FXML
    private DatePicker Date_creation;
    @FXML
    private TextField Description;
    @FXML
    private Button bt_submit;
    @FXML
    private ChoiceBox<String> type;

    private AjouterFeedback parentController;
    private final ServiceFeedback service = new ServiceFeedback();
    private Feedback selectedFeedback;

    @FXML
    public void initialize() {
        // Initialiser les types de feedback
        type.setItems(FXCollections.observableArrayList(
                "Non analysé", "Positif", "Négatif", "Neutre"
        ));

        // Conversion des émoticônes en emojis
        Description.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                String convertedText = EmojiUtils.replaceEmoticons(newValue);
                if (!convertedText.equals(newValue)) {
                    Description.setText(convertedText);
                }
            }
        });
    }

    public void initData(Feedback feedback, AjouterFeedback parent) {
        this.selectedFeedback = feedback;
        this.parentController = parent;
        Date_creation.setValue(LocalDate.now());
        Date_creation.setDisable(true); // Désactiver la modification
        Description.setText(feedback.getDescription());
        type.setValue(feedback.getType());
    }

    @FXML
    public void ModifFeedBack(ActionEvent event) {
        try {
            // Validation des champs
            if (Description.getText().isEmpty()) {
                showError("Erreur", "La description ne peut pas être vide.");
                return;
            } else if (type.getValue() == null) {
                showError("Erreur", "Veuillez sélectionner un type.");
                return;
            }

            // Mettre à jour le feedback
            selectedFeedback.setDescription(Description.getText());
            selectedFeedback.setType(type.getValue());
            service.update(selectedFeedback);

            // Rafraîchir la liste parente
            parentController.refreshListView();
            // Après la mise à jour réussie :
            Stage stage = (Stage) bt_submit.getScene().getWindow();
            stage.close();

            // Fermer la fenêtre
            ((Stage) bt_submit.getScene().getWindow()).close();
        } catch (Exception e) {
            showError("Erreur", e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Dans UpdateFeedback.java
    @FXML
    void handleCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}