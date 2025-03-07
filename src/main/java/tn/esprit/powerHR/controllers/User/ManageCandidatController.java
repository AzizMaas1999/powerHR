package tn.esprit.powerHR.controllers.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.User.Candidat;
import tn.esprit.powerHR.services.User.ServiceCandidat;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageCandidatController implements Initializable {
    @FXML
    private ImageView backIcon;

    @FXML
    private ListView<Candidat> list_Candidats;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView deleteIcon;

    @FXML
    private AnchorPane mainPane;

    private ObservableList<Candidat> originalList;
    private FilteredList<Candidat> filteredList;

    private ServiceCandidat sc = new ServiceCandidat();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCandidats();
        setupSearch();
        handleBackButton();
    }
    @FXML
    private void handleBackButton() {
        backIcon.setOnMouseClicked(event -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/MangeCandidat.fxml"));
                Parent previousPage = loader.load();

                Stage stage = (Stage) mainPane.getScene().getWindow();
                Scene scene = new Scene(previousPage);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadCandidats() {
        List<Candidat> candidats = sc.getAll();

        originalList = FXCollections.observableArrayList(candidats);
        filteredList = new FilteredList<>(originalList, p -> true);

        list_Candidats.setItems(filteredList);

        list_Candidats.setCellFactory(param -> new ListCell<Candidat>() {
            @Override
            protected void updateItem(Candidat candidat, boolean empty) {
                super.updateItem(candidat, empty);

                if (empty || candidat == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox();
                    hbox.setSpacing(10);

                    Text name = new Text(candidat.getNom() + " " + candidat.getPrenom());
                    Text email = new Text(candidat.getEmail());
                    Text phone = new Text(candidat.getTelephone());

                    Hyperlink cvLink = new Hyperlink("View CV");
                    cvLink.setOnAction(event -> openCV(candidat.getCvPdfUrl()));

                    Button deleteButton = new Button();
                    ImageView icon = new ImageView(deleteIcon.getImage());
                    icon.setFitWidth(16);
                    icon.setFitHeight(16);
                    deleteButton.setGraphic(icon);
                    deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                    deleteButton.setOnAction(event -> {
                        sc.delete(candidat);
                        originalList.remove(candidat);
                    });

                    hbox.getChildren().addAll(name, email, phone, cvLink, deleteButton);

                    setGraphic(hbox);
                }
            }
        });
    }

    private void openCV(String cvUrl) {
        if (cvUrl != null && !cvUrl.isEmpty()) {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(cvUrl));
            } catch (Exception e) {
                System.err.println("Erreur lors de l'ouverture du CV : " + e.getMessage());
            }
        } else {
            System.err.println("CV URL is invalid or empty.");
        }
    }


    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(candidat -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return candidat.getNom().toLowerCase().contains(lowerCaseFilter)
                        || candidat.getPrenom().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }

    @FXML
    private void handleSearch() {}
}
