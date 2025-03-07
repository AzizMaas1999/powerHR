package tn.esprit.powerHR.controllers.ClfrFeedback;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.ClfrFeedback.Feedback;
import tn.esprit.powerHR.services.ClfrFeedback.ServiceFeedback;
import tn.esprit.powerHR.utils.ClfrFeedback.EmojiUtils;
import javafx.event.ActionEvent;

import java.sql.Timestamp;
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
        // Initialiser les types de feedback dans la ChoiceBox
        type.setItems(FXCollections.observableArrayList(
                "Non analysé", "Positif", "Négatif", "Neutre"
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

    /**
     * Initialise les données du feedback à modifier.
     * La date est fixée à aujourd'hui et désactivée pour empêcher la modification.
     */
    public void initData(Feedback feedback, AjouterFeedback parent) {
        this.selectedFeedback = feedback;
        this.parentController = parent;
        Date_creation.setValue(LocalDate.now());
        Date_creation.setDisable(true);
        Description.setText(feedback.getDescription());
        type.setValue(feedback.getType());
    }

    /**
     * Méthode appelée lors du clic sur le bouton de modification.
     * Valide les champs, met à jour le feedback avec le timestamp actuel,
     * appelle la méthode de mise à jour du service et rafraîchit la ListView parente.
     */
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

            // Mettre à jour les valeurs du feedback
            selectedFeedback.setDescription(Description.getText());
            selectedFeedback.setType(type.getValue());
            // Mettre à jour le timestamp avec l'heure actuelle
            selectedFeedback.setDateCreation(new Timestamp(System.currentTimeMillis()));

            // Appeler le service pour mettre à jour le feedback en base
            service.update(selectedFeedback);

            // Rafraîchir la ListView dans le contrôleur parent
            parentController.refreshListView();

            // Fermer la fenêtre de modification
            Stage stage = (Stage) bt_submit.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            showError("Erreur", e.getMessage());
        }
    }

    /**
     * Affiche une alerte d'erreur avec le titre et le message fournis.
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Ferme la fenêtre en cas d'annulation.
     */
    @FXML
    void handleCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
