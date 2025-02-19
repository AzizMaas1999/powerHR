package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.models.Pointage;
import tn.esprit.powerHR.services.ServicePaie;
import tn.esprit.powerHR.services.ServicePointage;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class AddPaieController {

    @FXML
    private Button bt_ajouterpaie;

    @FXML
    private Button bt_modiferpaie;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_supppaie;

    @FXML
    private ListView<Paie> lv_paie;

    @FXML
    private TextField tf_heureEntree;

    @FXML
    private TextField tf_heureEntree1;

    @FXML
    private TextField tf_heureSortie;

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Paie p;
    public void setListPaie(Paie p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public Paie getListPaie() {
        return p;
    }

    @FXML
    public void initialize() {
        ServicePaie ps = new ServicePaie();
        try {
            List<Paie> list = ps.getAll();
            ObservableList<Paie> observableList = FXCollections.observableList(list);
            lv_paie.setItems(observableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void AjouterPaie(ActionEvent event) {
        ServicePaie ps = new ServicePaie();
        Paie p = new Paie();
        p.setNbjour(Integer.parseInt(tf_heureEntree.getText()));
        p.setMontant(Float.parseFloat(tf_heureEntree1.getText()));
        p.setMois(tf_heureSortie.getText());
        if((p.getNbjour()<30) && (p.getMontant()>0) && (!p.getMois().isEmpty())){
            try {
                ps.add(p);
                initialize();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Vérifiez les champs");
        }

    }

    @FXML
    void ModifierPage(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/UpdatePaie.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier Paie");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void Supp(ActionEvent event) {
        setListPaie(lv_paie.getSelectionModel().getSelectedItem());
        ServicePaie ps = new ServicePaie();
        try {
            ps.delete(getListPaie());
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
