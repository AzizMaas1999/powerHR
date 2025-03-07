package tn.esprit.powerHR.controllers.DemRepQuest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import tn.esprit.powerHR.controllers.User.OuvrierHomeController;
import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.DemRepQuest.Demande;
import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.services.DemRepQuest.DemandeService;
import tn.esprit.powerHR.services.User.ServiceEmploye;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private ComboBox<String> cb_type;

    @FXML
    private TextField re_id;

    @FXML
    private DatePicker dd_date;

    @FXML
    private DatePicker df_date;

    @FXML
    private ListView<Demande> lv_demande;
    private  DemandeService ds = new DemandeService();
    private ServiceEmploye empService = new ServiceEmploye();
    @FXML
    private TextField tf_cause;

    @FXML
    private TextField tf_salaire;


    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button bt_api;

    public ObservableList<Demande> observableList = FXCollections.observableArrayList();


    private Demande p;

    private Employe loggedInUser;

    public Employe getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Employe loggedInUser) {
        this.loggedInUser = loggedInUser;
        initialize();
    }

    public void setListDemande(Demande p) {
        this.p = p;
        System.out.println("Received Id: " + p);
    }

    public Demande getListDemande() {
        return p;
    }
    @FXML
    public void initialize() {
        loadDemandes();
        cb_type.setItems(FXCollections.observableArrayList("Augmentation Salaire", "Conges"));

        // Ajouter un écouteur pour détecter le changement de type
        cb_type.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Conges".equals(newValue)) {
                bt_api.setVisible(true);
            } else {
                bt_api.setVisible(false);
            }
        });

        // Cacher le bouton API par défaut
        bt_api.setVisible(false);
    }

    void loadDemandes() {
        try {
            DemandeService ds = new DemandeService();
            List<Demande> demandes = ds.getAll();
            System.out.println(getLoggedInUser().getUsername());

            List<Demande> demandesFiltrees = new ArrayList<>();
            for (Demande d : demandes) {
                if (d.getEmploye().getId() == getLoggedInUser().getId()) {
                    demandesFiltrees.add(d);
                }
            }

            observableList = FXCollections.observableArrayList(demandesFiltrees);
            lv_demande.setItems(observableList);

            // Définition de l'affichage personnalisé pour chaque demande
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
    void AjouterDemande(ActionEvent event) {
        DemandeService ds = new DemandeService();
        Demande d = new Demande();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        d.setDateCreation(Date.valueOf(LocalDate.now()));

        d.setType(cb_type.getValue());
        LocalDate today = LocalDate.now();
        if (dd_date.getValue() != null) {
            if (dd_date.getValue().isBefore(today)) {


                alert.setTitle("Date invalide");
                alert.setHeaderText("Erreur de sélection de date");
                alert.setContentText("Impossible de choisir une date antérieure à aujourd'hui !");
                alert.showAndWait();
                return;
            }
            d.setDateDebut(Date.valueOf(dd_date.getValue()));

        } else {
            d.setDateDebut(null);}

        if (df_date.getValue() != null) {
            d.setDateFin(Date.valueOf(df_date.getValue()));
        } else {

            d.setDateFin(null);
        }
        String cause = tf_cause.getText().trim();
        if (cause.length() < 15) {
            alert.setContentText( "La cause doit contenir au moins 15 caractères !");
            alert.showAndWait();

            return;
        }
        d.setCause(cause);

        d.setStatus("En Attente");


        String salaireText = tf_salaire.getText();
        float salaire = 0.0f;
        if (!salaireText.isEmpty()) {
            salaire = Float.parseFloat(salaireText);
        }
        d.setSalaire(salaire);


        d.setEmploye(getLoggedInUser());

        try {
            ds.add(d);
            initialize();
            dd_date.setValue(null);
            df_date.setValue(null);


            Alert alerts = new Alert(Alert.AlertType.INFORMATION);
            alerts.setTitle("Demande ajoutée");
            alerts.setHeaderText(null);
            alerts.setContentText("Votre demande a été ajoutée avec succès !");
            alerts.showAndWait();

        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout de la demande : " + e.getMessage());
        }
    }


    @FXML
    void NavigateModif(ActionEvent event) {
        try {
            // Load the addEmploye.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/ModifD.fxml"));
            Parent addEmployeView = loader.load();

            ModifController mc = loader.getController();
            mc.setLoggedInUser(getLoggedInUser());

            // Replace the current content of the mainPane with the addEmployeView
            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
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

    @FXML
    void searchDemande(KeyEvent event) {

        try {
            ObservableList<Demande> observableList1 = FXCollections.observableArrayList();

            for (Demande d : observableList) {
                if (d.getType().toLowerCase().contains(re_id.getText().toLowerCase())) {
                    observableList1.add(d);
                }
            }
            lv_demande.setItems(observableList1);

        } catch (Exception e) {
            System.out.println("Erreur chargement des demandes : " + e.getMessage());
        }
    }

    public void Retour(MouseEvent mouseEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/OuvrierHome.fxml"));
            Parent addEmployeView = loader.load();

            OuvrierHomeController oc = loader.getController();
            oc.setLoggedInUser(getLoggedInUser());


            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void BTAPI(MouseEvent event) {
        try {
            // Load the addEmploye.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DemRepQuest/tr.fxml"));
            Parent addEmployeView = loader.load();

            HolidayController hc = loader.getController();
            hc.setLoggedInUser(getLoggedInUser());

            // Replace the current content of the mainPane with the addEmployeView
            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
