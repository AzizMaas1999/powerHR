package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Feedback;
import tn.esprit.powerHR.services.ServiceFeedback;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.time.ZoneId; // Ajoute cette importation
import java.sql.Timestamp; // Ajoute cette importation
public class UpdateFeedback {

    @FXML
    private DatePicker Date_creation;
    @FXML
    private TextField Description;
    @FXML
    private Button bt_submit;
    @FXML
    private ListView<Feedback> lv_ShowFeedback;
    @FXML
    private ChoiceBox<String> type;

    private AjouterFeedback parentController;
    private final ServiceFeedback service = new ServiceFeedback();
    private Feedback selectedFeedback;


    private Feedback p;
    public void setListFeedback(Feedback p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public Feedback getListFeedback() {
        return p;
    }

    @FXML
    public void initialize() {
        // Initialiser le DatePicker avec la date du jour
        Date_creation.setValue(LocalDate.now());
        try {
            List<Feedback> list = service.getAll();
            ObservableList<Feedback> observableList = FXCollections.observableArrayList(list);
            lv_ShowFeedback.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void initData(Feedback feedback, AjouterFeedback parent) {
        this.selectedFeedback = feedback;
        this.parentController = parent;
        // Afficher la date enregistrée sans possibilité de modification
        Date_creation.setValue(feedback.getDateCreation().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
        Description.setText(feedback.getDescription());
        type.setValue(feedback.getType());
    }




    @FXML
    void ChooseLine(MouseEvent event) {
        Feedback p = lv_ShowFeedback.getSelectionModel().getSelectedItem();
        if (p != null) {
            Date_creation.setValue(p.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            Description.setText(p.getDescription());
            type.setValue(p.getType());
        }
    }
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void ModifFeedBack(ActionEvent event) {
        try {
            // Mise à jour du feedback sans modifier la date (puisqu'elle est fixée)
            selectedFeedback.setDescription(Description.getText());
            selectedFeedback.setType(type.getValue());
            service.update(selectedFeedback);
            // Rafraîchir la liste dans le contrôleur d'ajout
            parentController.refreshListView();
            // Fermer la fenêtre de modification
            ((Stage) Description.getScene().getWindow()).close();
        } catch (Exception e) {
            showError("Erreur de modification", e.getMessage());
        }
    }



    @FXML
    void NavigateAjout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/powerHR/views/AjouterFeedback.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ajouter Feedback");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }




}
