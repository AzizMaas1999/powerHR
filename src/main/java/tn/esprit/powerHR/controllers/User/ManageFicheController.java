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
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.User.FicheEmploye;
import tn.esprit.powerHR.services.User.ServiceFicheEmploye;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageFicheController implements Initializable {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private ListView<FicheEmploye> list_fiches;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView backIcon;

    @FXML
    private ImageView deleteIcon;

    @FXML
    private ImageView ModifIcon;

    private ObservableList<FicheEmploye> originalList;
    private FilteredList<FicheEmploye> filteredList;

    private ServiceFicheEmploye sf = new ServiceFicheEmploye();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFiches();
        setupSearch();
        handleBackButton();
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
    @FXML
    private void handleBackButton() {
        backIcon.setOnMouseClicked(event -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Modif.fxml"));
                Parent previousPage = loader.load();

                Stage stage = (Stage) mainPane.getScene().getWindow();
                Scene scene = new Scene(previousPage);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadFiches() {
        List<FicheEmploye> ficheEmployes = sf.getAll();

        originalList = FXCollections.observableArrayList(ficheEmployes);
        filteredList = new FilteredList<>(originalList, p -> true);

        list_fiches.setItems(filteredList);

        list_fiches.setCellFactory(param -> new ListCell<FicheEmploye>() {
            @Override
            protected void updateItem(FicheEmploye ficheEmploye, boolean empty) {
                super.updateItem(ficheEmploye, empty);

                if (empty || ficheEmploye == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox();
                    hbox.setSpacing(10);

                    Text cin = new Text(ficheEmploye.getCin());
                    Text name = new Text(ficheEmploye.getNom() + " " + ficheEmploye.getPrenom());
                    Text email = new Text(ficheEmploye.getEmail());
                    Text adresse = new Text(ficheEmploye.getAdresse());
                    Text city = new Text(ficheEmploye.getCity());
                    Text zip = new Text(ficheEmploye.getZip());
                    Text phone =new Text(ficheEmploye.getNumTel());
                    Hyperlink cv = new Hyperlink("View CV");
                    cv.setOnAction(event -> openCV(ficheEmploye.getCvPdfUrl()));

                    cin.setWrappingWidth(80);
                    name.setWrappingWidth(100);
                    email.setWrappingWidth(100);
                    adresse.setWrappingWidth(100);
                    city.setWrappingWidth(80);
                    zip.setWrappingWidth(80);
                    phone.setWrappingWidth(70);
                    cv.setMinWidth(70);

                    Button deleteButton = new Button();
                    ImageView icon = new ImageView(deleteIcon.getImage());
                    icon.setFitWidth(16);
                    icon.setFitHeight(16);
                    deleteButton.setGraphic(icon);
                    deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                    deleteButton.setOnAction(event -> {
                        sf.delete(ficheEmploye);
                        originalList.remove(ficheEmploye);
                    });
                    Button modifButton = new Button();
                    ImageView icon2 = new ImageView(ModifIcon.getImage());
                    icon2.setFitWidth(16);
                    icon2.setFitHeight(16);
                    modifButton.setGraphic(icon2);
                    modifButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                    modifButton.setOnAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ModifFiche.fxml"));
                            Parent modifFicheView = loader.load();

                            FicheModifController modifController = loader.getController();

                            modifController.setSelectedFiche(ficheEmploye);

                            mainPane.getChildren().setAll(modifFicheView);
                        } catch (IOException e) {
                            System.err.println("Error loading ModifFiche.fxml: " + e.getMessage());
                            e.printStackTrace();
                        }
                    });

                    hbox.getChildren().addAll(cin, name, email, adresse, city, zip, phone, cv, modifButton, deleteButton);

                    setGraphic(hbox);
                }
            }
        });
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(ficheEmploye -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return ficheEmploye.getNom().toLowerCase().contains(lowerCaseFilter)
                        || ficheEmploye.getPrenom().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }

    @FXML
    private void handleSearch() {}

    @FXML
    private void handleAddFiche() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AjoutFiche.fxml"));
            Parent addFicheView = loader.load();


            mainPane.getChildren().setAll(addFicheView);
        } catch (IOException e) {
            System.err.println("Error loading addFiche.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
