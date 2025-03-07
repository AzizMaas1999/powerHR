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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.DemRepQuest.RepQuestionnaire;
import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.services.DemRepQuest.ServiceRepQuestionnaire;

import java.io.IOException;
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
    private AnchorPane mainPane;

    @FXML
    private Button bt_supreponse;

    @FXML
    private DatePicker dc_date;

    @FXML
    private ListView<RepQuestionnaire> lv_RepQ;

    @FXML
    private TextField tf_Reponse;
    private ServiceRepQuestionnaire sq = new ServiceRepQuestionnaire();

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
        try {
            Employe employe = new Employe(3, "kk", "fdkbgkndfg", Poste.Charges, 445.2, "123456789125", "fdkbgkndfg", null, null, null, null, null);

            // Filtrer les réponses de l'employé connecté
            List<RepQuestionnaire> repQuest = sq.getAll().stream()
                    .filter(e -> e.getEmploye().getId() == employe.getId())
                    .toList();

            ObservableList<RepQuestionnaire> observableList = FXCollections.observableArrayList(repQuest);
            lv_RepQ.setItems(observableList);

            lv_RepQ.setCellFactory(param -> new ListCell<RepQuestionnaire>() {
                @Override
                protected void updateItem(RepQuestionnaire repQuestionnaire, boolean empty) {
                    super.updateItem(repQuestionnaire, empty);
                    if (empty || repQuestionnaire == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(20);

                        // Création des textes
                        Text dateReponse = new Text(repQuestionnaire.getDateCreation().toString());
                        Text reponse = new Text(repQuestionnaire.getReponse());

                        // Définition de la largeur pour un affichage correct
                        dateReponse.setWrappingWidth(270);
                        reponse.setWrappingWidth(260);

                        // Ajout des éléments au conteneur HBox
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
        if (rq != null) {
            tf_Reponse.setText(rq.getReponse());  // Remplir le champ avec la réponse sélectionnée
            setListRepQuestionnaire(rq);  // Mettre à jour l'objet sélectionné
        }}

    @FXML
    void NavigateAjoutR(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/AjoutRepQ.fxml"));
            Parent addEmployeView = loader.load();

            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    void ModiferReponse(ActionEvent event) {
        ServiceRepQuestionnaire ps = new ServiceRepQuestionnaire();
        RepQuestionnaire rq = getListRepQuestionnaire();
        rq.setReponse(tf_Reponse.getText());
        try {
            ps.update(rq);
          initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public void Retour(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/AjoutRepQ.fxml"));
            Parent addEmployeView = loader.load();

            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
