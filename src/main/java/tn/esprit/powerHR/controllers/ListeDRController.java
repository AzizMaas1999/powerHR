//import javafx.fxml.FXML;
////package tn.esprit.powerHR.controllers;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.geometry.Pos;
//import javafx.scene.Parent;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListCell;
//import javafx.scene.control.ListView;
//import javafx.scene.control.TextField;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Priority;
//import javafx.scene.text.Text;
//import tn.esprit.powerHR.models.Demande;
//import tn.esprit.powerHR.services.ApiService;
//import tn.esprit.powerHR.services.DemandeService;
//import tn.esprit.powerHR.services.ServiceEmploye;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
////public class ListeDRController {
////
////    @FXML
////    private TextField recherche_id;
////
////    @FXML
////    private ListView<Demande> lv_demandeEnAttente;
////
////    private final DemandeService ds = new DemandeService();
////    private final ServiceEmploye empService = new ServiceEmploye();
////    private final ApiService api = new ApiService();
////
////    @FXML
////    public void initialize() {
////        loadDemandes();
////    }
//@FXML
//private AnchorPane mainPane;
////    void loadDemandes() {
////        try {
////            List<Demande> list = ds.getAll();
////            List<Demande> demandesEnAttente = list.stream()
////                    .filter(demande -> "En Attente".equals(demande.getStatus()))
////                    .collect(Collectors.toList());
////
////            ObservableList<Demande> observableList = FXCollections.observableArrayList(demandesEnAttente);
////            afficherDemandes(observableList);
////        } catch (Exception e) {
////            System.out.println("Erreur lors de l'initialisation: " + e.getMessage());
////        }
////    }
////
////    private void afficherDemandes(ObservableList<Demande> observableList) {
////        try {
////            lv_demandeEnAttente.setItems(observableList);
////            lv_demandeEnAttente.setCellFactory(param -> new ListCell<Demande>() {
////                @Override
////                protected void updateItem(Demande item, boolean empty) {
////                    super.updateItem(item, empty);
////
////                    if (empty || item == null) {
////                        setText(null);
////                        setGraphic(null);
////                    } else {
////                        HBox hbox = new HBox(10);
////                        hbox.setAlignment(Pos.CENTER_LEFT);
////
////
////                        Text dateCreation = new Text(item.getDateCreation().toString());
////                        Text dateDebut = new Text(item.getDateDebut() != null ? item.getDateDebut().toString() : "N/A");
////                        Text dateFin = new Text(item.getDateFin() != null ? item.getDateFin().toString() : "N/A");
////                        Text salaire = new Text(String.valueOf(item.getSalaire()));
////                        Text status = new Text(item.getStatus());
////                        Text type = new Text(item.getType());
////                        Text cause = new Text(item.getCause());
////                        Text employe = new Text(empService.getById(item.getEmploye().getId()).getUsername());
////                        Text duree = new Text(api.Response(item.getDateDebut(), item.getDateFin()));
////
////
////                        dateCreation.setWrappingWidth(110);
////                        dateDebut.setWrappingWidth(100);
////                        dateFin.setWrappingWidth(100);
////                        salaire.setWrappingWidth(100);
////                        status.setWrappingWidth(100);
////                        type.setWrappingWidth(130);
////                        cause.setWrappingWidth(120);
////                        employe.setWrappingWidth(100);
////                        duree.setWrappingWidth(80);
////
////
////                        Button btnValider = new Button("Valider");
////                        Button btnRefuser = new Button("Refuser");
////                        btnValider.setStyle("-fx-background-color: green; -fx-text-fill: white;");
////                        btnRefuser.setStyle("-fx-background-color: red; -fx-text-fill: white;");
////
////                        btnValider.setOnAction(e -> validerDemande(item));
////                        btnRefuser.setOnAction(e -> refuserDemande(item));
////                        hbox.setAlignment(Pos.CENTER);
////                        hbox.getChildren().addAll(dateCreation, dateDebut, dateFin,  salaire, status, type, cause, employe,duree, btnValider, btnRefuser);
////                        HBox.setHgrow(dateCreation, Priority.ALWAYS);
////                        setGraphic(hbox);
////                    }
////                }
////            });
////        } catch (Exception e) {
////            System.out.println("Erreur lors de l'affichage des demandes: " + e.getMessage());
////        }
////    }
////
////    @FXML
////    public void validerDemande(Demande demande) {
////        demande.setStatus("Valider");
////        ds.update(demande);
////        System.out.println("Demande validée: " + demande.getId());
////        loadDemandes();
////    }
////
////    @FXML
////    public void refuserDemande(Demande demande) {
////        demande.setStatus("Refuser");
////        ds.update(demande);
////        System.out.println("Demande refusée: " + demande.getId());
////        loadDemandes();
////    }
////    @FXML
////    void searchDemande(KeyEvent event) {
////        try {
////            List<Demande> list = ds.getAll();
////            ObservableList<Demande> observableList = FXCollections.observableArrayList(list);
////            ObservableList<Demande> observableList1 = FXCollections.observableArrayList();
////
////            for (Demande d : observableList) {
////                if (d.getStatus().equals("En Attente") && d.getEmploye().getUsername().contains(recherche_id.getText())) {
////                    observableList1.add(d);
////                }
////            }
////            lv_demandeEnAttente.setItems(observableList1);
////
////        } catch (Exception e) {
////            System.out.println("Erreur chargement des demandes : " + e.getMessage());
////        }
////    }
//@FXML
//void statistique(MouseEvent event) {
//    try {
//        // Load the addEmploye.fxml file
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tr.fxml"));
//        Parent addEmployeView = loader.load();
//
//        // Replace the current content of the mainPane with the addEmployeView
//        mainPane.getChildren().setAll(addEmployeView);
//    } catch (IOException e) {
//        System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
//        e.printStackTrace();
//    }
//}
//
////
////    }
////
////
