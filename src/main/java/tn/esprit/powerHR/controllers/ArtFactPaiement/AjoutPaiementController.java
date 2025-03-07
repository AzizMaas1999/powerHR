package tn.esprit.powerHR.controllers.ArtFactPaiement;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tn.esprit.powerHR.models.ArtFactPaiement.Facture;
import tn.esprit.powerHR.models.ArtFactPaiement.Paiement;
import tn.esprit.powerHR.services.ArtFactPaiement.ServiceArticle;
import tn.esprit.powerHR.services.ArtFactPaiement.ServiceFacture;
import tn.esprit.powerHR.services.ArtFactPaiement.ServicePaiement;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

public class AjoutPaiementController {

    public Label lblMessage;
    @FXML
    private Button bt_ajouterpaiement, bt_modiferpaiement, bt_submit, bt_supppaiement;

    @FXML
    private ComboBox<String> cb_mode;

    @FXML
    private DatePicker dp_date;

    @FXML
    private AnchorPane mainPane;


    @FXML
    private TextField tf_recherche;

    private ObservableList<Paiement> paiements = FXCollections.observableArrayList();


    @FXML
    private TableView<Paiement> tv_paiement;

    @FXML
    private TableColumn<Paiement, String> col_reference;

    @FXML
    private TableColumn<Paiement, Date> col_date;

    @FXML
    private TableColumn<Paiement, Double> col_montant;

    @FXML
    private TableColumn<Paiement, String> col_mode;

    @FXML
    private TextField tf_montant, tf_reference;

    private ServicePaiement ps = new ServicePaiement();
    private ObservableList<Paiement> paiement = FXCollections.observableArrayList();


    @FXML
    private TableView<Facture> tv_factures;
    @FXML
    private TableColumn<Facture, String> col_num, col_type;
    @FXML
    private TableColumn<Facture, Float> col_total;

    @FXML
    private TextField tf_recherche1;


    private final ServiceFacture serviceFacture = new ServiceFacture();
    private ObservableList<Facture> factures = FXCollections.observableArrayList();

    private ServiceArticle sa = new ServiceArticle();


    @FXML
    void rechercherPaiement() {
        String keyword = tf_recherche.getText().toLowerCase().trim();

        // Filtrer les paiements avec Java Streams
        List<Paiement> filteredList = paiements.stream()
                .filter(p -> p.getReference().toLowerCase().contains(keyword) ||
                        p.getMode().toLowerCase().contains(keyword) ||
                        String.valueOf(p.getMontant()).contains(keyword) ||
                        p.getDate().toString().contains(keyword))
                .toList(); // Convertir en liste immuable

        // Mettre à jour la TableView
        tv_paiement.setItems(FXCollections.observableArrayList(filteredList));
    }

    @FXML
    void rechercherFactures() {
        String keyword = tf_recherche1.getText().toLowerCase().trim();
        List<Facture> filteredList = factures.stream()
                .filter(f -> f.getNum().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
        tv_factures.setItems(FXCollections.observableArrayList(filteredList));

        tv_factures.setItems(FXCollections.observableArrayList(filteredList));

    }

    @FXML
    public void initialize() {
        col_reference.setCellValueFactory(new PropertyValueFactory<>("reference"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        col_mode.setCellValueFactory(new PropertyValueFactory<>("mode"));

        // Charger les paiements depuis la base de données
        paiements.setAll(ps.getAll());
        tv_paiement.setItems(paiements);


        // Écouteur pour filtrer à chaque modification du champ de recherche
        tf_recherche.textProperty().addListener((observable, oldValue, newValue) -> rechercherPaiement());
        tf_recherche1.textProperty().addListener((observable, oldValue, newValue) -> rechercherFactures());
        // Ajouter les modes de paiement
        cb_mode.setItems(FXCollections.observableArrayList("chèque", "espèce", "virement", "traite"));

        // Initialisation des colonnes du TableView
        col_reference.setCellValueFactory(new PropertyValueFactory<>("reference"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        col_mode.setCellValueFactory(new PropertyValueFactory<>("mode"));

        // Charger les paiements existants
        refreshTable();

        col_num.setCellValueFactory(new PropertyValueFactory<>("num"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("typeFact"));

        factures.setAll(serviceFacture.getAll());
        tv_factures.setItems(factures);


        Stripe.apiKey = "sk_test_51Qz88XLp4Kjx6gr23O7wtIFaoP3BBRenSyXexAdhGLyHnif5lnEcHxlunX3CvAxv5GktelCZ708X32ImQipQGkt700xzJpIQ48";


    }

    private void refreshTable() {
        paiements.setAll(ps.getAll()); // Récupérer les paiements mis à jour depuis la base
        tv_paiement.setItems(paiements);
    }


    @FXML
    void Ajout(ActionEvent event) {
        if (dp_date.getValue() == null || tv_factures.getColumns().isEmpty() || tf_reference.getText().isEmpty() || cb_mode.getValue() == null || tf_montant.getText().isEmpty()) {
            System.out.println(" Veuillez remplir tous les champs !");
            return;
        }

        try {
            Paiement p = new Paiement();
            p.setDate(Date.valueOf(dp_date.getValue()));
            p.setMode(cb_mode.getValue());
            Facture selectedFacture = tv_factures.getSelectionModel().getSelectedItem();
            if (selectedFacture != null) {
                p.setMontant(selectedFacture.getTotal());
            } else {
                System.out.println("Veuillez sélectionner une facture !");
                return;
            }
            p.setReference(tf_reference.getText());
            p.setMontant(Float.parseFloat(tf_montant.getText()));


            ps.add(p);

            // Ajouter directement dans la table en utilisant `stream()`
            paiements.add(p);
            tv_paiement.setItems(paiements.stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));

            // Réinitialisation des champs
            dp_date.setValue(null);
            cb_mode.setValue(null);
            tf_montant.clear();
            tf_reference.clear();

            System.out.println("Paiement ajouté avec succès !");
        } catch (NumberFormatException e) {
            System.out.println("Erreur : Veuillez entrer un montant valide.");
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @FXML
    void ChooseLine(MouseEvent event) {
        Facture selectedFacture = tv_factures.getSelectionModel().getSelectedItem();

        if (selectedFacture != null) {
            tf_montant.setText(String.valueOf(selectedFacture.getTotal()));
        }
    }

    @FXML
    void NavModif(ActionEvent event) {
        try {
            // Load the addEmploye.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/ModifPaiement.fxml"));
            Parent addEmployeView = loader.load();

            // Replace the current content of the mainPane with the addEmployeView
            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void Supp(ActionEvent event) {
        Optional<Paiement> selectedPaiement = Optional.ofNullable(tv_paiement.getSelectionModel().getSelectedItem());

        selectedPaiement.ifPresentOrElse(paiement -> {
            try {
                ps.delete(paiement);

                // Suppression avec `removeIf()` et Stream
                paiements.removeIf(p -> p.getReference().equals(paiement.getReference()));
                tv_paiement.setItems(paiements.stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));

                System.out.println(" Paiement supprimé avec succès !");
            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression : " + e.getMessage());
            }
        }, () -> System.out.println(" Veuillez sélectionner un paiement à supprimer."));
    }

    public void Retour(MouseEvent mouseEvent) {
        try {
            // Load the addEmploye.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/FactArtPaieHome.fxml"));
            Parent addEmployeView = loader.load();

            // Replace the current content of the mainPane with the addEmployeView
            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void Navpaiement(ActionEvent actionEvent) {
    }




        public void effectuerPaiement() {
            String montantStr = tf_montant.getText().trim();

            if (montantStr.isEmpty()) {
                afficherAlerte("Erreur", "Veuillez entrer un montant !");
                return;
            }

            try {
                float montant = Float.parseFloat(montantStr);
                if (montant <= 0) {
                    afficherAlerte("Erreur", "Le montant doit être positif !");
                    return;
                }

                //  Remplacez votre clé API ici (évitez de l'exposer en dur dans le code)
                Stripe.apiKey = "sk_test_51Qz88XLp4Kjx6gr23O7wtIFaoP3BBRenSyXexAdhGLyHnif5lnEcHxlunX3CvAxv5GktelCZ708X32ImQipQGkt700xzJpIQ48";

                long montantCents = (long) (montant * 100);
                System.out.println("Montant en centimes : " + montantCents);

                //  URL de redirection après paiement
                String successUrl = "http://localhost:8080/succes";
                String cancelUrl = "http://localhost:8080/cancel";

                //  Création de la session Stripe Checkout
                SessionCreateParams params = SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(successUrl)
                        .setCancelUrl(cancelUrl)
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("eur")
                                                        .setUnitAmount(montantCents)
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName("Paiement Facture")
                                                                        .build()
                                                        )
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();

                Session session = Session.create(params);
                System.out.println("🔗 Lien de paiement Stripe : " + session.getUrl());

                //  Ouvrir la page de paiement dans le navigateur
                Desktop.getDesktop().browse(new URI(session.getUrl()));

                lblMessage.setText("Veuillez finaliser votre paiement dans la fenêtre ouverte...");

                //  Vérifier le paiement après 30 secondes pour éviter un faux négatif
                new Thread(() -> {
                    try {
                        Thread.sleep(30000); // Augmenté à 30 secondes
                        Platform.runLater(() -> verifierStatutPaiement(session.getId()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

            } catch (NumberFormatException e) {
                afficherAlerte("Erreur", "Montant invalide, entrez un nombre !");
            } catch (StripeException e) {
                afficherAlerte("Erreur Stripe", "Erreur : " + e.getMessage());
                e.printStackTrace();
            } catch (IOException | URISyntaxException e) {
                afficherAlerte("Erreur Système", "Problème : " + e.getMessage());
                e.printStackTrace();
            }
        }

        public void verifierStatutPaiement(String sessionId) {
            try {
                //  Récupération de la session Stripe
                Session session = Session.retrieve(sessionId);

                System.out.println("⚡ Statut du paiement Stripe : " + session.getStatus());

                if ("complete".equals(session.getStatus())) {
                    Platform.runLater(() -> {
                        lblMessage.setText("✅ Paiement confirmé !");

                        // 📝 Sauvegarde dans la base de données
                        Paiement nouveauPaiement = new Paiement();
                        nouveauPaiement.setReference("REF-" + System.currentTimeMillis()); // Référence unique
                        nouveauPaiement.setDate(new Date(System.currentTimeMillis())); // Date actuelle
                        nouveauPaiement.setMontant(Float.parseFloat(tf_montant.getText()));
                        nouveauPaiement.setMode("En ligne"); // Mode de paiement Stripe

                        ps.add(nouveauPaiement); // Ajout en base
                        paiements.add(nouveauPaiement); // Ajout dans la TableView

                        refreshTable(); // Rafraîchir l'affichage
                    });
                } else {
                    Platform.runLater(() -> lblMessage.setText("❌ Paiement non confirmé ! Statut : " + session.getStatus()));
                }
            } catch (StripeException e) {
                afficherAlerte("Erreur Stripe", "Erreur de vérification du paiement !");
                e.printStackTrace();
            }
        }

        // 📢 Méthode pour afficher une alerte (à adapter avec votre système d'alertes)
        private void afficherAlerte(String titre, String message) {
            System.out.println("⚠️ " + titre + " : " + message);
            lblMessage.setText(message);
        }
    }


