package tn.esprit.powerHR.controllers.ClfrFeedback;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.ClfrFeedback.CLFr;
import tn.esprit.powerHR.models.User.*;
import tn.esprit.powerHR.services.ClfrFeedback.ServiceCLFr;

import java.io.File;
import java.io.IOException;
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

    @FXML
    private TextField photoPathField;
    @FXML
    private Button btnChoisirPhoto;



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

        // Appliquer la cellule personnalisée à la ListView
        lv_ShowCLFr.setCellFactory(param -> new CLFrListCell());

        // Charger les données dans la ListView
        loadClientFournisseurData();

        // Chargement initial de la ListView et des statistiques
        loadCLFrList();
        updateStats();

        // Ajout d'un écouteur pour la recherche en temps réel
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterCLFrList());
    }
    // Méthode pour charger les données dans la ListView
    private void loadClientFournisseurData() {
        // Utiliser un service pour récupérer les données des clients/fournisseurs
        ServiceCLFr service = new ServiceCLFr(); // Assure-toi du bon nom du service
        ObservableList<CLFr> clientsFournisseurs = FXCollections.observableArrayList(service.getAll()); // Remplacer `add()` par `getAll()`

        // Ajouter les données à la ListView
        lv_ShowCLFr.setItems(clientsFournisseurs); // Utilisation correcte de la liste
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
            clfr.setType(type2.getValue());// "Client" ou "Fournisseur"
            // Ajout du chemin de la photo (non en blob)
            if (!photoPathField.getText().trim().isEmpty()) {
                clfr.setPhotoPath(photoPathField.getText().trim());
            }

            // Simulation d'un objet Employe (à remplacer par une récupération réelle si nécessaire)
            Employe employe = new Employe(1, "fdkbgkndfg", "fdkbgkndfg", Poste.Charges, 445.2, "123456789125", "fdkbgkndfg", null, null, null, null, null);
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
        CLFr selected = lv_ShowCLFr.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez sélectionner un élément !");
            alert.show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClfrFeedback/modifierCLFr.fxml"));
            Parent root = loader.load();

            ModifierCLFr controller = loader.getController();
            controller.initData(selected, this); // Passer une référence de AjouterCLFr

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }


    // Méthode pour rafraîchir la ListView
    public void refreshListView() {
        ServiceCLFr service = new ServiceCLFr();
        try {
            List<CLFr> list = service.getAll();
            lv_ShowCLFr.setItems(FXCollections.observableArrayList(list));
            lv_ShowCLFr.refresh();
        } catch (Exception e) {
            System.out.println("Erreur rafraîchissement: " + e.getMessage());
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
            int clients = serviceCLFr.countByType("Client");
            int fournisseurs = serviceCLFr.countByType("Fournisseur");
            int total = clients + fournisseurs; // total calculé comme somme des deux types
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
    @FXML
    private void choisirPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        // Filtrer pour les fichiers image
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Fichiers Image", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        // Récupérer la fenêtre actuelle à partir d'un nœud de la scène (ici bt_ajouterCLFr)
        Stage stage = (Stage) bt_ajouterCLFr.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            // Affiche le chemin absolu dans le TextField
            photoPathField.setText(file.getAbsolutePath());
        }
    }
    @FXML
    private void openChatBot(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClfrFeedback/ChatBot.fxml"));
            Parent root = loader.load();

            // Optionnel : envoyer un contexte spécifique au ChatBot (par exemple, Clients/Fournisseurs)
            ChatBotController controller = loader.getController();
            controller.setContext("Vous êtes dans le module Clients/Fournisseurs. "
                    + "Vous pouvez me demander comment ajouter, modifier ou supprimer un client/fournisseur, "
                    + "des conseils sur la recherche et le filtrage, ou encore l'interprétation des statistiques affichées.");

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Assistance Clients/Fournisseurs");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Vous pouvez aussi afficher une alerte en cas d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible d'ouvrir l'assistance");
            alert.setContentText("Une erreur est survenue lors de l'ouverture du ChatBot.");
            alert.showAndWait();
        }
    }


}

