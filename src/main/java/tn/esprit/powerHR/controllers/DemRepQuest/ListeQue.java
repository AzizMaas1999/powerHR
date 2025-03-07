package tn.esprit.powerHR.controllers.DemRepQuest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.DemRepQuest.Questionnaire;
import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.services.DemRepQuest.ServiceQuestionnaire;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class ListeQue {

    @FXML
    private ListView<Questionnaire> id_listev;

    @FXML
    private AnchorPane mainPane;


    private ServiceQuestionnaire sq = new ServiceQuestionnaire();
    private ObservableList<Questionnaire> listQuestionnaires;

    public void initialize() {
        loadQuestionnaires();
    }

    void loadQuestionnaires() {
        try {
            Employe employe = new Employe(3 , "kk", "fdkbgkndfg", Poste.Charges, 445.2, "123456789125", "fdkbgkndfg", null, null, null, null, null);

            List<Questionnaire> questionnaires = sq.getAll().stream()
                    .filter(e->e.getEmploye().getId() == employe.getId())
                    .toList();
            listQuestionnaires = FXCollections.observableArrayList(questionnaires);
            id_listev.setItems(listQuestionnaires);

            id_listev.setCellFactory(param -> new ListCell<Questionnaire>() {
                @Override
                protected void updateItem(Questionnaire questionnaire, boolean empty) {
                    super.updateItem(questionnaire, empty);

                    if (empty || questionnaire == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox();
                        hbox.setSpacing(20);

                        Text objet = new Text(questionnaire.getObjet());
                        Text description = new Text(questionnaire.getDescription());
                        Text dateCreation = new Text(questionnaire.getDateCreation().toString());

                        objet.setWrappingWidth(190);
                        description.setWrappingWidth(300);
                        dateCreation.setWrappingWidth(220);

                        hbox.getChildren().addAll(dateCreation, objet, description);
                        setGraphic(hbox);

                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des questionnaires : " + e.getMessage());
        }
    }
    @FXML
    void listevi(MouseEvent event) {
        Questionnaire q = id_listev.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/AjoutRepQ.fxml"));
            Parent addView = loader.load();
            System.out.println(q);

            AjoutRepQ controller = loader.getController();
            controller.setLabel(q.getDateCreation(),q.getObjet(),q.getDescription());

            mainPane.getChildren().setAll(addView);
        } catch (IOException e) {
            System.err.println("Error loading : " + e.getMessage());
        }
    }
    public void Retour(MouseEvent mouseEvent) {
        try {
            // Load the addEmploye.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/OuvrierHome.fxml"));
            Parent addEmployeView = loader.load();

            // Replace the current content of the mainPane with the addEmployeView
            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
