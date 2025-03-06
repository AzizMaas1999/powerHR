package tn.esprit.powerHR.controllers.ClfrFeedback;

import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.powerHR.models.ClfrFeedback.CLFr;

public class CLFrListCell extends ListCell<CLFr> {

    private HBox content;
    private Label nomLabel;
    private Label matriculeLabel;
    private Label adresseLabel;
    private Label numTelLabel;
    private ImageView imageView;

    public CLFrListCell() {
        super();

        // Initialisation des labels pour chaque colonne
        nomLabel = new Label();
        nomLabel.setStyle("-fx-font-weight: bold; -fx-padding: 5;");
        nomLabel.setPrefWidth(200); // Ajustez selon le besoin

        matriculeLabel = new Label();
        matriculeLabel.setStyle("-fx-padding: 5;");
        matriculeLabel.setPrefWidth(150); // Ajustez la largeur

        adresseLabel = new Label();
        adresseLabel.setStyle("-fx-padding: 5;");
        adresseLabel.setPrefWidth(250); // Ajustez la largeur

        numTelLabel = new Label();
        numTelLabel.setStyle("-fx-padding: 5;");
        numTelLabel.setPrefWidth(150); // Ajustez la largeur

        // ImageView pour afficher la photo
        imageView = new ImageView();
        imageView.setFitHeight(40); // Ajustez la hauteur de l'image
        imageView.setFitWidth(40); // Ajustez la largeur de l'image
        imageView.setPreserveRatio(true); // Pour préserver le ratio de l'image

        // Conteneur HBox pour organiser les labels et l'image horizontalement
        content = new HBox(10, imageView, nomLabel, matriculeLabel, adresseLabel, numTelLabel); // Ajout de l'ImageView
        content.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ddd; -fx-border-width: 1;");
        content.setPrefHeight(50);  // Hauteur de la ligne
    }

    @Override
    protected void updateItem(CLFr item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            // Mise à jour des labels avec les informations du client/fournisseur
            nomLabel.setText(item.getNom());
            matriculeLabel.setText(item.getMatriculeFiscale());
            adresseLabel.setText(item.getAdresse());
            numTelLabel.setText(item.getNumTel());

            // Charger l'image depuis le chemin absolu
            String photoPath = item.getPhotoPath();
            if (photoPath != null && !photoPath.isEmpty()) {
                Image image = new Image("file:" + photoPath); // Chemin absolu
                imageView.setImage(image);
            } else {
                imageView.setImage(null);  // Si pas de photo, afficher aucune image
            }

            // Définir l'élément graphique de la cellule
            setGraphic(content);
        } else {
            setGraphic(null);  // Si l'élément est vide, ne rien afficher
        }
    }
}
