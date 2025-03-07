package tn.esprit.powerHR.controllers.DemRepQuest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.DemRepQuest.Questionnaire;
import tn.esprit.powerHR.models.DemRepQuest.RepQuestionnaire;
import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.services.DemRepQuest.ServiceRepQuestionnaire;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AjoutRepQ {

    @FXML
    private Button bt_submit;

    @FXML
    private Label lb_date;

    @FXML
    private Label lb_desc;

    @FXML
    private Label lb_obj;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ListView<RepQuestionnaire> id_listeview;


    @FXML
    private TextField tf_Reponse;

    private ServiceRepQuestionnaire sq = new ServiceRepQuestionnaire();
    private ObservableList<RepQuestionnaire> listRepQuestionnaires;


    public void initialize() {
        loadRepQuestionnaires();
    }

    void loadRepQuestionnaires() {
        try {
            Employe employe = new Employe(3, "kk", "fdkbgkndfg", Poste.Charges, 445.2, "123456789125", "fdkbgkndfg", null, null, null, null, null);

            List<RepQuestionnaire> RepQuest = sq.getAll().stream()
                    .filter(e->e.getEmploye().getId() == employe.getId())
                    .toList();
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

                        reponse.setWrappingWidth(190);

                        dateCreation.setWrappingWidth(300);

                        hbox.getChildren().addAll(dateCreation,reponse);
                        setGraphic(hbox);

                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des questionnaires : " + e.getMessage());
        }
    }

    public void setLabel(Date date, String obj, String desc) {
        lb_date.setText(String.valueOf(date));
        lb_desc.setText(desc);
        lb_obj.setText(obj);
    }

    @FXML
    void AjouterReponse(ActionEvent event) {
        ServiceRepQuestionnaire sq = new ServiceRepQuestionnaire();
        RepQuestionnaire rq = new RepQuestionnaire();

        String reponse = tf_Reponse.getText().trim();

        if (reponse.length() < 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La réponse doit contenir au moins 10 caractères !");
            alert.showAndWait();
            return;
        }

        rq.setDateCreation(Date.valueOf(LocalDate.now()));
        rq.setReponse(reponse);
        Questionnaire questionnaire = new Questionnaire(1, null, null, null, null);
        rq.setQuestionnaire(questionnaire);
        Employe employe = new Employe(3, "kk", "fdkbgkndfg", Poste.Charges, 445.2, "123456789125", "fdkbgkndfg", null, null, null, null, null);
        rq.setEmploye(employe);

        try {
            sq.add(rq);
            initialize();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("La réponse a été ajoutée avec succès.");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void Retour(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/listequest.fxml"));
            Parent addEmployeView = loader.load();

            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
