package tn.esprit.powerHR.controllers.DemRepQuest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import tn.esprit.powerHR.models.DemRepQuest.Questionnaire;
import tn.esprit.powerHR.services.DemRepQuest.ServiceQuestionnaire;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class ModifQ {

    @FXML
    private Button bt_ajouterQuestionnaire;

    @FXML
    private Button bt_modiferquestionnaire;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_supppquestionnaire;

    @FXML
    private DatePicker dc_date;

    @FXML
    private ListView<Questionnaire> lv_ques;

    @FXML
    private TextField tf_Objet;

    @FXML
    private TextField tf_desc;

    @FXML
    private AnchorPane mainPane;

    private Questionnaire q;

    public void setListquestionnaire(Questionnaire q) {
        this.q = q;
        System.out.println("Received Id: " + q);
    }

    public Questionnaire getListquestionnaire() {
        return q;
    }
    private ServiceQuestionnaire sq = new ServiceQuestionnaire();
    private ObservableList<Questionnaire> listQuestionnaires;

    @FXML
    public void initialize() {
        loadQuestionnaires();
    }

    private void loadQuestionnaires() {
        try {
            List<Questionnaire> questionnaires = sq.getAll();
            listQuestionnaires = FXCollections.observableArrayList(questionnaires);
            lv_ques.setItems(listQuestionnaires);

            lv_ques.setCellFactory(param -> new ListCell<Questionnaire>() {
                @Override
                protected void updateItem(Questionnaire questionnaire, boolean empty) {
                    super.updateItem(questionnaire, empty);
                    if (empty || questionnaire == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(15);
                        Text dateCreation = new Text(questionnaire.getDateCreation().toString());
                        Text objet = new Text(questionnaire.getObjet());
                        Text description = new Text(questionnaire.getDescription());

                        objet.setWrappingWidth(150);
                        description.setWrappingWidth(260);
                        dateCreation.setWrappingWidth(180);

                        hbox.getChildren().addAll(dateCreation, objet, description);
                        setGraphic(hbox);
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Error loading questionnaires: " + e.getMessage());

    }
}

    @FXML
    void ChooseLine(MouseEvent event) {
       Questionnaire q = lv_ques.getSelectionModel().getSelectedItem();

            tf_Objet.setText(q.getObjet());
            tf_desc.setText(q.getDescription());
            setListquestionnaire(q);
        }


    @FXML
    void ModifierQuestionnaire(ActionEvent event) {
        ServiceQuestionnaire ps = new ServiceQuestionnaire();
        Questionnaire q = getListquestionnaire();

        q.setObjet(tf_Objet.getText());
        q.setDescription(tf_desc.getText());
        try {
            ps.update(q);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }}


    @FXML
    void NavigateAjout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/AjoutQ.fxml"));
            Parent addEmployeView = loader.load();

            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void Retour(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/AjoutQ.fxml"));
            Parent addEmployeView = loader.load();

            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

