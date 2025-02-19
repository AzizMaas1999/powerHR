package tn.esprit.powerHR.controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Demande;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.services.DemandeService;

import java.sql.Date;
import java.util.List;

public class AjoutController {

    @FXML
    private Button bt_ajouterdemande;

    @FXML
    private Button bt_modiferdemande;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_suppdemande;

    @FXML
    private ComboBox<String> cb_status;


    @FXML
    private ComboBox<String> cb_type;


    @FXML
    private DatePicker dc_date;

    @FXML
    private DatePicker dd_date;

    @FXML
    private DatePicker df_date;

    @FXML
    private ListView<Demande> lv_demande;

    @FXML
    private TextField tf_cause;

    @FXML
    private TextField tf_salaire;

    private Demande p;

    public void setListDemande(Demande p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }

    public Demande getListDemande() {
        return p;
    }


    @FXML
    public void initialize() {

        ObservableList<String> modes = FXCollections.observableArrayList("Augmentation Salaire",
                "Conges"
        );
        cb_type.setItems(modes);

        ObservableList<String> mode = FXCollections.observableArrayList("Valider",
                "En Attente", "Refuser"
        );
        cb_status.setItems(mode);

        DemandeService ds = new DemandeService();
        try {
            List<Demande> list = ds.getAll();
            ObservableList<Demande> observableList = FXCollections.observableList(list);
            lv_demande.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void AjouterDemande(ActionEvent event) {

        if (dc_date.getValue() == null || dd_date.getValue() == null || df_date.getValue() == null) {
            System.out.println("Erreur : Toutes les dates doivent être renseignées !");
            return;
        }

        if (cb_type.getValue().isEmpty()) {
            System.out.println("Erreur : Le type de demande doit être sélectionné !");
            return;
        }

        if (tf_salaire.getText().isEmpty()) {
            System.out.println("Erreur : Le champ salaire ne peut pas être vide !");
            return;
        }

        try {
            Float.parseFloat(tf_salaire.getText());
        } catch (NumberFormatException e) {
            System.out.println("Erreur : Veuillez entrer un nombre valide pour le salaire !");
            return;
        }

        if (tf_cause.getText().isEmpty()) {
            System.out.println("Erreur : Le champ cause ne peut pas être vide !");
            return;
        }

        if (cb_status.getValue().isEmpty()) {
            System.out.println("Erreur : Le statut de la demande doit être sélectionné !");
            return;
        }


        DemandeService ds = new DemandeService();
        Demande d = new Demande();
        d.setDateCreation(Date.valueOf(dc_date.getValue()));
        d.setType(cb_type.getValue());
        d.setDateDebut(Date.valueOf(dd_date.getValue()));
        d.setDateFin(Date.valueOf(df_date.getValue()));
        d.setSalaire(Float.parseFloat(tf_salaire.getText()));
        d.setCause(tf_cause.getText());
        d.setStatus(cb_status.getValue());

        Employe employe = new Employe(2, "fdkbgkndfg", "fdkbgkndfg", "chargesRH", 445.2, "123456789125", "fdkbgkndfg");
        d.setEmploye(employe);

        try {
            ds.add(d);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void NavigateModif(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ModifD.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier Demande");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


    }

    @FXML
    void Supp(ActionEvent event) {
        setListDemande(lv_demande.getSelectionModel().getSelectedItem());
        DemandeService ps = new DemandeService();
        try {
            ps.delete(getListDemande());
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
