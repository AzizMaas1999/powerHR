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
import tn.esprit.powerHR.models.DemRepQuest.Demande;
import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.services.DemRepQuest.DemandeService;
import tn.esprit.powerHR.services.User.ServiceEmploye;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ModifController {

    @FXML
    private Button bt_ajouterdemande;

    @FXML
    private Button bt_modiferdemande;

    @FXML
    private Button bt_submit;

    @FXML
    private Button bt_suppdemande;

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

    @FXML
    private TextField tf_status;

    @FXML
    private AnchorPane mainPane;

    private Demande p;

    public void setListDemande(Demande p) {
        this.p = p;
        System.out.println("Received Id: " + p);
    }

    public Demande getListDemande() {
        return p;
    }

    private ServiceEmploye empService = new ServiceEmploye();

    private Employe loggedInUser;

    public Employe getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Employe loggedInUser) {
        this.loggedInUser = loggedInUser;
        initialize();
    }

    @FXML
    public void initialize() {
        loadDemandes();
    }


    void loadDemandes() {
        try {
            DemandeService ds = new DemandeService();
            List<Demande> demandes = ds.getAll();

            List<Demande> demandesFiltrees = new ArrayList<>();
            for (Demande d : demandes) {
                if (d.getEmploye().getId() == getLoggedInUser().getId()) {
                    demandesFiltrees.add(d);
                }
            }

            ObservableList<Demande> observableList = FXCollections.observableArrayList(demandesFiltrees);
            lv_demande.setItems(observableList);

            lv_demande.setCellFactory(param -> new ListCell<Demande>() {
                @Override
                protected void updateItem(Demande item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox();
                        hbox.setSpacing(10);

                        Text dateCreation = new Text(item.getDateCreation().toString());
                        Text dateDebut = new Text(item.getDateDebut() != null ? item.getDateDebut().toString() : "N/A");
                        Text dateFin = new Text(item.getDateFin() != null ? item.getDateFin().toString() : "N/A");
                        Text salaire = new Text(String.valueOf(item.getSalaire()));
                        Text status = new Text(item.getStatus());
                        Text type = new Text(item.getType());
                        Text cause = new Text(item.getCause());
                        Text employe = new Text(empService.getById(item.getEmploye().getId()).getUsername());

                        dateCreation.setWrappingWidth(130);
                        dateDebut.setWrappingWidth(120);
                        dateFin.setWrappingWidth(130);
                        salaire.setWrappingWidth(100);
                        status.setWrappingWidth(100);
                        type.setWrappingWidth(130);
                        cause.setWrappingWidth(120);
                        employe.setWrappingWidth(120);

                        hbox.getChildren().addAll(dateCreation, dateDebut, dateFin, salaire, status, type, cause, employe);
                        setGraphic(hbox);
                    }
                }
            });

        } catch (Exception e) {
            System.out.println("Erreur chargement des demandes : " + e.getMessage());
        }
    }

    @FXML
    void ChooseLine(MouseEvent event) {
        Demande p = lv_demande.getSelectionModel().getSelectedItem();
        if (p != null) {
            if (p.getDateDebut() != null) {
                dd_date.setValue(p.getDateDebut().toLocalDate());
            } else {
                dd_date.setValue(null);
            }

            if (p.getDateFin() != null) {
                df_date.setValue(p.getDateFin().toLocalDate());
            } else {
                df_date.setValue(null);
            }

            cb_type.setValue(p.getType());
            tf_cause.setText(p.getCause());
            tf_salaire.setText(String.valueOf(p.getSalaire()));
            setListDemande(p);

        }
    }

    @FXML
    void ModifierDemande(ActionEvent event) {
        DemandeService ps = new DemandeService();
        Demande d = getListDemande();
        d.setType((String) cb_type.getValue());
        d.setDateDebut(Date.valueOf(dd_date.getValue()));
        d.setDateFin(Date.valueOf(df_date.getValue()));
        d.setSalaire(Float.parseFloat(tf_salaire.getText()));
        d.setCause(tf_cause.getText());

        try {
            ps.update(p);
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void NavigateAjout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/AjoutD.fxml"));
            Parent addEmployeView = loader.load();

            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }
    public void Retour(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/AjoutD.fxml"));
            Parent addEmployeView = loader.load();

            AjoutController ajoutController = loader.getController();
            ajoutController.setLoggedInUser(getLoggedInUser());

            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}