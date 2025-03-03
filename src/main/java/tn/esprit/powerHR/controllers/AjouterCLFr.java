package tn.esprit.powerHR.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.services.ServiceCLFr;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import javafx.scene.text.Text;

public class AjouterCLFr {

    @FXML
    private TextField NumTel;

    @FXML
    private TextField adresse;

    @FXML
    private Button bt_ajouterCLFr;

    @FXML
    private Button bt_modiferCLFr;

    @FXML
    private Button bt_submit2;

    @FXML
    private Button bt_supprimerCLFr;

    @FXML
    private ListView<CLFr> lv_ShowCLFr;

    @FXML
    private TextField matriculeFicale;

    @FXML
    private TextField nom;
    @FXML
    private TextField searchField;
    // Ajouter ces annotations @FXML
    @FXML private Text totalCLFrText;
    @FXML private Text clientsCountText;
    @FXML private Text fournisseursCountText;



    // La ChoiceBox contient désormais "Client" et "Fournisseur"
    @FXML
    private ChoiceBox<String> type2;

    private CLFr p;
    public void setListClFr(CLFr p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }

    public CLFr getListClFr() {
        return p;
    }
    @FXML
    private void filterCLFrList() {
        String searchText = searchField.getText().toLowerCase().trim();
        ServiceCLFr serviceCLFr = new ServiceCLFr();

        try {
            List<CLFr> allItems = serviceCLFr.getAll();
            List<CLFr> filteredList = allItems.stream()
                    .filter(clfr -> clfr.getNom().toLowerCase().contains(searchText) ||
                            clfr.getAdresse().toLowerCase().contains(searchText) ||
                            clfr.getMatriculeFiscale().toLowerCase().contains(searchText) ||
                            clfr.getNumTel().toLowerCase().contains(searchText) ||
                            clfr.getType().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());

            lv_ShowCLFr.setItems(FXCollections.observableArrayList(filteredList));
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche : " + e.getMessage());
        }
    }



    @FXML
    public void initialize() {
        // Remplissage de la ChoiceBox (unique ajout)
        type2.getItems().setAll("Client", "Fournisseur");


        // Chargement initial de la ListView et des statistiques
        loadCLFrList();
        updateStats();
        // Configuration de la cell factory pour personnaliser l'affichage des lignes
        lv_ShowCLFr.setCellFactory(listView -> new ListCell<CLFr>() {
            @Override
            protected void updateItem(CLFr item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox vbox = new VBox(5); // Espacement entre les éléments

                    Label nomLabel = new Label("Nom: " + item.getNom());
                    Label matriculeLabel = new Label("Matricule Fiscale: " + item.getMatriculeFiscale());
                    Label adresseLabel = new Label("Adresse: " + item.getAdresse());
                    Label numTelLabel = new Label("Téléphone: " + item.getNumTel());
                    Label typeLabel = new Label("Type: " + item.getType());

                    // Appliquer du style pour améliorer la lisibilité
                    nomLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                    matriculeLabel.setStyle("-fx-font-size: 13px;");
                    adresseLabel.setStyle("-fx-font-size: 13px;");
                    numTelLabel.setStyle("-fx-font-size: 13px;");
                    typeLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: blue;");

                    vbox.getChildren().addAll(nomLabel, matriculeLabel, adresseLabel, numTelLabel, typeLabel);
                    vbox.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

                    setGraphic(vbox);
                }
            }
        });


        // Ajout d'un écouteur pour la recherche en temps réel
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterCLFrList());
    }


    @FXML
    void AjouterCLFr(ActionEvent event) {
        ServiceCLFr serviceCLFr = new ServiceCLFr();
        System.out.println("Bouton AjouterCLFr cliqué !");
        try {
            // Vérification que tous les champs sont remplis
            if (nom.getText().trim().isEmpty() ||
                    matriculeFicale.getText().trim().isEmpty() ||
                    adresse.getText().trim().isEmpty() ||
                    NumTel.getText().trim().isEmpty() ||
                    type2.getValue() == null) {

                // Affichage de l'alerte si un champ est vide
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Champ manquant");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;  // Arrêter l'exécution si la saisie est invalide
            }

            // Création et affectation des valeurs à l'objet CLFr
            CLFr clfr = new CLFr();
            clfr.setNom(nom.getText());
            clfr.setMatriculeFiscale(matriculeFicale.getText());
            clfr.setAdresse(adresse.getText());
            clfr.setNumTel(NumTel.getText());
            clfr.setType(type2.getValue()); // "Client" ou "Fournisseur"

            // Simulation d'un objet Employe (à remplacer par une récupération réelle si nécessaire)
            Employe employe = new Employe(1, "fdkbgkndfg", "fdkbgkndfg", "chargesRH", 445.2, "123456789125", "fdkbgkndfg");
            clfr.setEmploye(employe);

            // Ajout du CLFr via le service
            serviceCLFr.add(clfr);
            System.out.println("CLFr ajouté avec succès !");

            // Rafraîchissement de la ListView pour afficher le nouveau CLFr
            refreshAll();

            // Affichage d'une alerte de succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Ajout réussi");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Le CLFr a été ajouté avec succès.");
            successAlert.showAndWait();
            nom.clear();
            matriculeFicale.clear();
            adresse.clear();
            NumTel.clear();
            type2.setValue(null);

        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout du CLFr : " + e.getMessage());

            // Affichage d'une alerte en cas d'erreur
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Une erreur est survenue lors de l'ajout du CLFr.");
            errorAlert.showAndWait();
        }

    }

    @FXML
    void NavigateModifier(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/modifierCLFr.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier ClFr");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void Supp(ActionEvent event) {
        setListClFr(lv_ShowCLFr.getSelectionModel().getSelectedItem());
        ServiceCLFr ps = new ServiceCLFr();
        try {
            ps.delete(getListClFr());
            //initialize();
            refreshAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
    @FXML
    private Label totalCLFrLabel;

    /*private void updateStats() {
        ServiceCLFr serviceCLFr = new ServiceCLFr();

        int totalCLFr = serviceCLFr.getAll().size();
        totalCLFrLabel.setText("Total CLFr: " + totalCLFr);
    }*/
    private void updateStats() {
        ServiceCLFr serviceCLFr = new ServiceCLFr();
        try {
            int total = serviceCLFr.getAll().size();
            int clients = serviceCLFr.countByType("Client");
            int fournisseurs = serviceCLFr.countByType("Fournisseur");

            totalCLFrText.setText(String.valueOf(total));
            clientsCountText.setText(String.valueOf(clients));
            fournisseursCountText.setText(String.valueOf(fournisseurs));
        } catch (Exception e) {
            System.out.println("Erreur stats: " + e.getMessage());
        }
    }
    private void refreshAll() {
        loadCLFrList();
        updateStats();
    }

    // Ajoutez ces imports en haut du fichier
    private void loadCLFrList() {
        ServiceCLFr serviceCLFr = new ServiceCLFr();
        try {
            List<CLFr> list = serviceCLFr.getAll();
            ObservableList<CLFr> observableList = FXCollections.observableList(list);
            lv_ShowCLFr.setItems(observableList);
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des CLFr : " + e.getMessage());
        }
    }

   /// Méthode corrigée
    private void filterCLFrList(String searchText) {
        ServiceCLFr serviceCLFr = new ServiceCLFr();
        try {
            List<CLFr> allItems = serviceCLFr.getAll();

            List<CLFr> filteredList = allItems.stream()
                    .filter(clfr -> {
                        String lowerText = searchText.toLowerCase();
                        return Stream.of(
                                        clfr.getNom().toLowerCase(),
                                        clfr.getAdresse().toLowerCase(),
                                        clfr.getMatriculeFiscale().toLowerCase(),
                                        clfr.getNumTel().toLowerCase(),
                                        clfr.getType().toLowerCase()
                                )
                                .anyMatch(field -> field.contains(lowerText));
                    })
                    .collect(Collectors.toList()); // Nécessite aussi l'import Collectors

            lv_ShowCLFr.setItems(FXCollections.observableArrayList(filteredList));
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche: " + e.getMessage());
        }
    }
}

