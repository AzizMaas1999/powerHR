package tn.esprit.powerHR.controllers.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.services.User.ServiceEmploye;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageEmployeController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ListView<Employe> list_employes;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView deleteIcon;

    @FXML
    private ImageView ModifIcon;

    @FXML
    private ImageView backIcon;

    private ObservableList<Employe> originalList;
    private FilteredList<Employe> filteredList;

    private ServiceEmploye se = new ServiceEmploye();

    private Employe loggedInUser;

    public Employe getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Employe loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadEmployes();
        setupSearch();
        handleBackButton();
    }
        @FXML
        private void handleBackButton() {
            backIcon.setOnMouseClicked(event -> {
                try {

                    FXMLLoader loader = null;
                    if (getLoggedInUser().getPoste().equals(Poste.Charges)) {
                        loader = new FXMLLoader(getClass().getResource("/User/ChargesHome.fxml"));
                        Parent previousPage = loader.load();

                        ChargesHomeController chargesHomeController = loader.getController();
                        chargesHomeController.setLoggedInUser(getLoggedInUser());

                        Stage stage = (Stage) mainPane.getScene().getWindow();
                        Scene scene = new Scene(previousPage);
                        stage.setScene(scene);
                    }
                    if (getLoggedInUser().getPoste().equals(Poste.Directeur)) {
                        loader = new FXMLLoader(getClass().getResource("/User/DirecteurHome.fxml"));
                        Parent previousPage = loader.load();

                        DirecteurHomeController directeurHomeController = loader.getController();
                        directeurHomeController.setLoggedInUser(getLoggedInUser());



                        Stage stage = (Stage) mainPane.getScene().getWindow();
                        Scene scene = new Scene(previousPage);
                        stage.setScene(scene);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        private void loadEmployes() {
        try {
            List<Employe> employes = se.getAll();

            originalList = FXCollections.observableArrayList(employes);
            filteredList = new FilteredList<>(originalList, p -> true);

            list_employes.setItems(filteredList);

            list_employes.setCellFactory(param -> new ListCell<Employe>() {
                @Override
                protected void updateItem(Employe employe, boolean empty) {
                    super.updateItem(employe, empty);

                    if (empty || employe == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox();
                        hbox.setSpacing(10);

                        Text username = new Text(employe.getUsername());
                        Text password = new Text(employe.getPassword());
                        Text salaire = new Text(String.valueOf(employe.getSalaire()));
                        Text poste = new Text(String.valueOf(employe.getPoste()));
                        Text rib = new Text(employe.getRib());
                        Text codeSociale = new Text(employe.getCodeSociale());
                        Text nomDepartement = new Text(employe.getDepartement());

                        username.setWrappingWidth(110);
                        password.setWrappingWidth(110);
                        salaire.setWrappingWidth(100);
                        poste.setWrappingWidth(90);
                        rib.setWrappingWidth(110);
                        codeSociale.setWrappingWidth(110);
                        nomDepartement.setWrappingWidth(110);

                        Button deleteButton = new Button();
                        ImageView icon = new ImageView(deleteIcon.getImage());
                        icon.setFitWidth(16);
                        icon.setFitHeight(16);
                        deleteButton.setGraphic(icon);
                        deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                        deleteButton.setOnAction(event -> {
                            try {
                                se.delete(employe);
                                originalList.remove(employe);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        Button modifButton = new Button();
                        ImageView icon2 = new ImageView(ModifIcon.getImage());
                        icon2.setFitWidth(16);
                        icon2.setFitHeight(16);
                        modifButton.setGraphic(icon2);
                        modifButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                        modifButton.setOnAction(event -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Modif.fxml"));
                                Parent modifEmployeView = loader.load();

                                EmployeModifController modifController = loader.getController();

                                modifController.setSelectedEmploye(employe);
                                modifController.setLoggedInUser(getLoggedInUser());

                                mainPane.getChildren().setAll(modifEmployeView);
                            } catch (IOException e) {
                                System.err.println("Error loading Modif.fxml: " + e.getMessage());
                                e.printStackTrace();
                            }
                        });


                        hbox.getChildren().addAll(username, password, salaire, poste, rib, codeSociale, nomDepartement, modifButton, deleteButton);
                        setGraphic(hbox);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(employe -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return employe.getUsername().toLowerCase().contains(lowerCaseFilter)
                        || employe.getDepartement().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }

    @FXML
    private void handleSearch() {}

    @FXML
    private void handleAddEmployee() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Ajout.fxml"));
            Parent addEmployeView = loader.load();

            EmployeAjoutController ajoutController = loader.getController();
            ajoutController.setLoggedInUser(getLoggedInUser());

            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading Ajout.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
