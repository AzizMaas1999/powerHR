package tn.esprit.powerHR.controllers.DemRepQuest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import tn.esprit.powerHR.models.DemRepQuest.RepQuestionnaire;
import javafx.scene.input.MouseEvent;
import tn.esprit.powerHR.services.DemRepQuest.ServiceRepQuestionnaire;
import tn.esprit.powerHR.services.User.ServiceEmploye;

import java.io.IOException;
import java.util.List;

public class ListeRepQuestController {

    @FXML
    private ListView<RepQuestionnaire> id_listeview;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField tf_Re;

    private ServiceRepQuestionnaire sq = new ServiceRepQuestionnaire();
    private ObservableList<RepQuestionnaire> listRepQuestionnaires;
       private ServiceEmploye empService = new ServiceEmploye();



    public void initialize() {
        loadRepQuestionnaires();
    }

    void loadRepQuestionnaires() {
        try {

            List<RepQuestionnaire> RepQuest = sq.getAll();

            listRepQuestionnaires = FXCollections.observableArrayList(RepQuest);
            id_listeview.setItems(listRepQuestionnaires);

            id_listeview.setCellFactory(param -> new ListCell<RepQuestionnaire>() {
                @Override
                protected void updateItem(RepQuestionnaire repQuestionnaire, boolean empty) {
                    super.updateItem(repQuestionnaire, empty);

                    if (empty || repQuestionnaire == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox();
                        hbox.setSpacing(20);

                        Text reponse= new Text(repQuestionnaire.getReponse());
                        Text dateCreation = new Text(repQuestionnaire.getDateCreation().toString());
                       Text employe = new Text(empService.getById(repQuestionnaire.getEmploye().getId()).getUsername());

                        reponse.setWrappingWidth(190);

                        dateCreation.setWrappingWidth(200);

                        hbox.getChildren().addAll(dateCreation,reponse,employe);
                        setGraphic(hbox);

                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des questionnaires : " + e.getMessage());
        }
    }
    public void Retour(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/DemQuestRepHome.fxml"));
            Parent addEmployeView = loader.load();


            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }


     @FXML
    void recherche(KeyEvent event) {

        try {
            List<RepQuestionnaire> list = sq.getAll();
            ObservableList<RepQuestionnaire> observableList = FXCollections.observableArrayList(list);
            ObservableList<RepQuestionnaire> observableList1 = FXCollections.observableArrayList();

            for (RepQuestionnaire R : observableList) {
                if (R.getEmploye().getUsername().contains(tf_Re.getText())) {
                    observableList1.add(R);
                }
            }
            id_listeview.setItems(observableList1);

        } catch (Exception e) {
            System.out.println("Erreur chargement des demandes : " + e.getMessage());
        }
    }
}
