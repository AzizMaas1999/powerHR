package tn.esprit.powerHR.controllers.ArtFactPaiement;

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
import javafx.stage.Stage;
import tn.esprit.powerHR.models.ArtFactPaiement.Paiement;
import tn.esprit.powerHR.services.ArtFactPaiement.ServicePaiement;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ModifPaiementController {

    @FXML
    private Button bt_ajouterpaiement, bt_modiferpaiement, bt_submit, bt_supppaiement;

    @FXML
    private ComboBox<String> cb_mode;

    @FXML
    private DatePicker dp_date;

    @FXML
    private AnchorPane mainPane;


    @FXML
    private TableView<Paiement> tv_paiement;

    @FXML
    private TableColumn<Paiement, String> col_reference;

    @FXML
    private TableColumn<Paiement, Double> col_montant;

    @FXML
    private TableColumn<Paiement, String> col_mode;

    @FXML
    private TableColumn<Paiement, Date> col_date;

    @FXML
    private TextField tf_montant, tf_reference;

    private Paiement p;

    @FXML
    public void initialize() {
        // Vérification si la TableView est bien chargée
        if (tv_paiement == null) {
            System.out.println("Erreur : TableView est NULL !");
            return;
        }

        // Initialisation des valeurs du ComboBox
        ObservableList<String> modes = FXCollections.observableArrayList("cheque", "espece", "virement", "traite");
        cb_mode.setItems(modes);

        // Association des colonnes aux attributs du modèle
        col_reference.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getReference()));
        col_montant.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getMontant()));
        col_mode.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMode()));
        col_date.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDate()));

        // Charger les données
        loadTableData();
    }

    private void loadTableData() {
        ServicePaiement ps = new ServicePaiement();
        try {
            List<Paiement> list = ps.getAll();
            ObservableList<Paiement> observableList = FXCollections.observableArrayList(list.stream().collect(Collectors.toList()));
            tv_paiement.setItems(observableList);
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des paiements : " + e.getMessage());
        }
    }

    @FXML
    void ChooseLine(MouseEvent event) {
        Paiement selectedPaiement = tv_paiement.getSelectionModel().getSelectedItem();
        if (selectedPaiement != null) {
            dp_date.setValue(selectedPaiement.getDate().toLocalDate());
            cb_mode.setValue(selectedPaiement.getMode());
            tf_reference.setText(selectedPaiement.getReference());
            tf_montant.setText(String.valueOf(selectedPaiement.getMontant()));
            setListPaiement(selectedPaiement);
        } else {
            System.out.println("Aucun paiement sélectionné !");
        }
    }

    @FXML
    void Modifer(ActionEvent event) {
        if (p == null) {
            System.out.println("Erreur : Aucun paiement sélectionné !");
            return;
        }

        // Vérification avant mise à jour
        if (dp_date.getValue() == null || cb_mode.getValue() == null || tf_reference.getText().isEmpty() || tf_montant.getText().isEmpty()) {
            System.out.println("Erreur : Tous les champs doivent être remplis !");
            return;
        }

        try {
            p.setDate(Date.valueOf(dp_date.getValue()));
            p.setMode(cb_mode.getValue());
            p.setReference(tf_reference.getText());
            p.setMontant(Double.parseDouble(tf_montant.getText()));

            // Vérifie que l'ID du paiement n'est pas null
            if (p.getId() == 0) {
                System.out.println("Erreur : ID du paiement non défini !");
                return;
            }

            ServicePaiement ps = new ServicePaiement();
            ps.update(p);
            loadTableData(); // Rafraîchir la TableView

            System.out.println("Paiement mis à jour avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour du paiement : " + e.getMessage());
        }
    }


    @FXML
    void NavAjout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/AjoutPaiement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ajouter Paiement");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ouverture de la fenêtre d'ajout : " + e.getMessage());
        }
    }

    public void setListPaiement(Paiement p) {
        this.p = p;
        System.out.println("Paiement sélectionné : " + p);
    }

    public Paiement getListPaiement() {
        return p;
    }

    public void Supp(ActionEvent actionEvent) {
        Paiement selectedPaiement = tv_paiement.getSelectionModel().getSelectedItem();
        if (selectedPaiement == null) {
            System.out.println("Erreur : Aucun paiement sélectionné !");
            return;
        }

        try {
            ServicePaiement ps = new ServicePaiement();
            ps.delete(selectedPaiement);
            loadTableData();
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    public void Retour(MouseEvent mouseEvent) {
        try {
            // Load the addEmploye.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/AjoutPaiement.fxml"));
            Parent addEmployeView = loader.load();

            // Replace the current content of the mainPane with the addEmployeView
            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
