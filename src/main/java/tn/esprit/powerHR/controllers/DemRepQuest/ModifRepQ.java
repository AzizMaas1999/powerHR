package tn.esprit.powerHR.controllers.DemRepQuest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.DemRepQuest.RepQuestionnaire;
import tn.esprit.powerHR.services.DemRepQuest.ServiceRepQuestionnaire;

import java.sql.Date;
import java.util.List;

public class ModifRepQ {

    @FXML
    private Button bt_ajouterreponse;

    @FXML
    private Button bt_modiferrponse;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_supreponse;

    @FXML
    private DatePicker dc_date;

    @FXML
    private ListView<RepQuestionnaire> lv_RepQ;

    @FXML
    private TextField tf_Reponse;

    private RepQuestionnaire p;
    public void setListRepQuestionnaire(RepQuestionnaire p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public RepQuestionnaire getListRepQuestionnaire() {
        return p;
    }

    @FXML
    public void initialize() {
        loadRepQuestionnaires();
    }

    private void loadRepQuestionnaires() {
        ServiceRepQuestionnaire ps = new ServiceRepQuestionnaire();
        try {
            List<RepQuestionnaire> repQuestionnaires = ps.getAll();
            ObservableList<RepQuestionnaire> observableList = FXCollections.observableArrayList(repQuestionnaires);
            lv_RepQ.setItems(observableList);

            lv_RepQ.setCellFactory(param -> new ListCell<RepQuestionnaire>() {
                @Override
                protected void updateItem(RepQuestionnaire repQuestionnaire, boolean empty) {
                    super.updateItem(repQuestionnaire, empty);
                    if (empty || repQuestionnaire == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(15);

                        Text dateReponse = new Text(repQuestionnaire.getDateCreation().toString());
                        Text reponse = new Text(repQuestionnaire.getReponse());

                        dateReponse.setWrappingWidth(200);
                        reponse.setWrappingWidth(260);


                        hbox.getChildren().addAll(dateReponse, reponse);
                        setGraphic(hbox);
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des réponses aux questionnaires: " + e.getMessage());
        }
    }


    @FXML
    void ChooseLine(MouseEvent event) {
        RepQuestionnaire rq = lv_RepQ.getSelectionModel().getSelectedItem();

        rq.setReponse(tf_Reponse.getText());
        setListRepQuestionnaire(p);

    }

    @FXML
    void AjoutNav(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/DemRepQuest/AjoutRepQ.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Ajouter Reponse Questionnaire");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void ModiferReponse(ActionEvent event) {
        ServiceRepQuestionnaire ps = new ServiceRepQuestionnaire();
        RepQuestionnaire rq = getListRepQuestionnaire();
        rq.setReponse(tf_Reponse.getText());
        try {
            ps.update(p);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
