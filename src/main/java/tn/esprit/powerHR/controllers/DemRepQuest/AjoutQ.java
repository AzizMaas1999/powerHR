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
import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.services.DemRepQuest.ServiceQuestionnaire;
import tn.esprit.powerHR.services.User.ServiceEmploye;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;



public class AjoutQ {

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
    private ListView<Questionnaire> lv_ajoutQ;

    @FXML
    private TextField tf_Objet;

    @FXML
    private AnchorPane mainePane;

    @FXML
    private ComboBox<Employe> cb_emp;

    @FXML
    private TextField tf_rech;

    @FXML
    private TextField tf_desc;

    private ServiceQuestionnaire sq = new ServiceQuestionnaire();
    private ObservableList<Questionnaire> listQuestionnaires;
    private Questionnaire selectedQuestionnaire;

    private Employe loggedInUser;

    public Employe getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Employe loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void initialize() {
        loadQuestionnaires();
        ServiceEmploye se = new ServiceEmploye();
        List<Employe> employes = se.getAll().stream()
                .toList();
        cb_emp.setItems(FXCollections.observableArrayList(employes));
    }

    void loadQuestionnaires() {
        try {
            List<Questionnaire> questionnaires = sq.getAll();
            listQuestionnaires = FXCollections.observableArrayList(questionnaires);
            lv_ajoutQ.setItems(listQuestionnaires);

            lv_ajoutQ.setCellFactory(param -> new ListCell<Questionnaire>() {
                @Override
                protected void updateItem(Questionnaire questionnaire, boolean empty) {
                    super.updateItem(questionnaire, empty);

                    if (empty || questionnaire == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox();
                        hbox.setSpacing(15);

                        Text objet = new Text(questionnaire.getObjet());
                        Text description = new Text(questionnaire.getDescription());
                        Text dateCreation = new Text(questionnaire.getDateCreation().toString());
                        Text employe = new Text(questionnaire.getEmploye().getUsername());

                        objet.setWrappingWidth(130);
                        description.setWrappingWidth(160);
                        dateCreation.setWrappingWidth(170);

                        hbox.getChildren().addAll(dateCreation,objet, description,employe);
                        setGraphic(hbox);
                    }
                }
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void AjouterQuestionnaire(ActionEvent event) {
        Questionnaire q = new Questionnaire();
        q.setDateCreation(Date.valueOf(LocalDate.now()));
        q.setObjet(tf_Objet.getText());
        q.setDescription(tf_desc.getText());
        q.setEmploye(cb_emp.getSelectionModel().getSelectedItem());

        if (q.getObjet().length() < 15) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur Objet");
            alert.setHeaderText("L'objet est trop court");
            alert.setContentText("L'objet doit contenir au moins 15 caractères.");
            alert.showAndWait();
            return;
        }

        if (q.getDescription().length() < 15) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur Description");
            alert.setHeaderText("La description est trop courte");
            alert.setContentText("La description doit contenir au moins 15 caractères.");
            alert.showAndWait();
            return;
        }

        try {
            sq.add(q);
            loadQuestionnaires();


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le questionnaire a été ajouté avec succès.");
            alert.showAndWait();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de l'ajout du questionnaire");
            alert.setContentText("Une erreur est survenue lors de l'ajout : " + e.getMessage());
            alert.showAndWait();
        }


}

    @FXML
    void NavigateModif(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/ModifQ.fxml"));
            Parent addEmployeView = loader.load();


            mainePane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void Supp(ActionEvent event) {
        selectedQuestionnaire = lv_ajoutQ.getSelectionModel().getSelectedItem();

        if (selectedQuestionnaire == null) {
            System.out.println("Veuillez sélectionner un questionnaire !");
            return;
        }

        try {
            sq.delete(selectedQuestionnaire);
            loadQuestionnaires();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le questionnaire a été supprimé avec succès.");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    public void Retour(MouseEvent mouseEvent) {
        try {

            FXMLLoader loader = null;
            if (getLoggedInUser().getPoste().equals(Poste.Directeur)) {
                loader = new FXMLLoader(getClass().getResource("/User/DirecteurHome.fxml"));
            }
            if (getLoggedInUser().getPoste().equals(Poste.Charges)) {
                loader = new FXMLLoader(getClass().getResource("/User/ChargesHome.fxml"));
            }
            Parent addEmployeView = loader.load();


            mainePane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    void rechercheQues(ActionEvent event) {
        try {
            List<Questionnaire> list = sq.getAll();
            ObservableList<Questionnaire> observableList = FXCollections.observableArrayList(list);
            ObservableList<Questionnaire> observableList1 = FXCollections.observableArrayList();

            for (Questionnaire q : observableList) {
                if (q.getEmploye().getUsername().toLowerCase().contains(tf_rech.getText())) {
                    observableList1.add(q);
                }
            }
            lv_ajoutQ.setItems(observableList1);

        } catch (Exception e) {
            System.out.println("Erreur chargement des demandes : " + e.getMessage());
        }

    }
}
