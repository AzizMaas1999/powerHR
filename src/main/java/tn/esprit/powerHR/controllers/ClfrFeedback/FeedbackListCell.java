package tn.esprit.powerHR.controllers.ClfrFeedback;

import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import java.text.SimpleDateFormat;
import tn.esprit.powerHR.models.ClfrFeedback.Feedback;

public class FeedbackListCell extends ListCell<Feedback> {
    private HBox content;
    private Label descriptionLabel;
    private Label dateLabel;
    private Label typeLabel;

    public FeedbackListCell() {
        super();
        // Largeurs fixes pour aligner avec l'en-tête
        descriptionLabel = new Label();
        descriptionLabel.setPrefWidth(400);
        dateLabel = new Label();
        dateLabel.setPrefWidth(250);
        typeLabel = new Label();
        typeLabel.setPrefWidth(150);

        content = new HBox(0, descriptionLabel, dateLabel, typeLabel);
        content.setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    protected void updateItem(Feedback feedback, boolean empty) {
        super.updateItem(feedback, empty);
        if (empty || feedback == null) {
            setGraphic(null);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDate = sdf.format(feedback.getDateCreation());
            descriptionLabel.setText(feedback.getDescription());
            dateLabel.setText(formattedDate);
            typeLabel.setText(feedback.getType());
            setGraphic(content);
        }
    }
}
