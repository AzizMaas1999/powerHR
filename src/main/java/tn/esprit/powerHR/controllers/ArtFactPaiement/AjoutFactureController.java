package tn.esprit.powerHR.controllers.ArtFactPaiement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.json.JSONObject;
import tn.esprit.powerHR.models.ArtFactPaiement.Article;
import tn.esprit.powerHR.models.ArtFactPaiement.Facture;
import tn.esprit.powerHR.models.ArtFactPaiement.Paiement;
import tn.esprit.powerHR.models.ClfrFeedback.CLFr;
import tn.esprit.powerHR.services.ArtFactPaiement.ServiceArticle;
import tn.esprit.powerHR.services.ArtFactPaiement.ServiceFacture;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AjoutFactureController {

    @FXML
    private Button bt_ajouterfacture, bt_modiferfacture, bt_suppfacture;

    @FXML
    private VBox vbox;

    @FXML
    private ComboBox<String> cb_typefact;

    @FXML
    private DatePicker dp_date;

    @FXML
    private TextField tf_num, tf_total, tf_recherche, tf_montant;

    @FXML
    private TextField tf_recherchearticle;

    @FXML
    private ComboBox<Article> cb_articles;

    @FXML
    private ComboBox<String> cb_devise;

    @FXML
    private ListView<Article> lv_articles;

    @FXML
    private AnchorPane mainPane;


    @FXML
    private Label lbl_resultat;


    List<Article> articleSelectionne = new ArrayList<>();
    Label lbl_article = new Label();





    @FXML
    private TableView<Facture> tv_facture;
    private static final String API_KEY = "2fd5102a84894dc29b1ae9debe4c8418";
    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=" + API_KEY;

    @FXML
    private TableColumn<Facture, String> col_num, col_type;

    @FXML
    private TableColumn<Facture, Date> col_date;

    @FXML
    private TableColumn<Facture, Float> col_total;

    private  ServiceFacture ps = new ServiceFacture();
    private  ServiceArticle sa = new ServiceArticle();
    private  ObservableList<Facture> factures = FXCollections.observableArrayList();
    private  ObservableList<Article> articles = FXCollections.observableArrayList();
    private  ObservableList<Article> articless = FXCollections.observableArrayList();
    private  java.util.Map<Article, TextField> articleQuantities = new java.util.HashMap<>();


    @FXML
    public void initialize() {
        // Configurer les colonnes
        col_num.setCellValueFactory(new PropertyValueFactory<>("num"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("typeFact"));

        lv_articles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        vbox.getChildren().add(3,lbl_article);


        // Charger les factures
        factures.setAll(ps.getAll());
        tv_facture.setItems(factures);

        // Charger les articles
//        articles.setAll(sa.getAll());
//        lv_articles.setItems(articles);

        // Initialisation des types de facture
        cb_typefact.setItems(FXCollections.observableArrayList("Facture", "Avoir"));

        // Initialisation des devises
        cb_devise.setItems(FXCollections.observableArrayList("USD", "EUR", "GBP", "TND","SAR"));

        // Recherche dynamique
        tf_recherche.textProperty().addListener((observable, oldValue, newValue) -> rechercherFacture());

        tf_recherchearticle.textProperty().addListener((observable, oldValue, newValue) -> rechercherArticle());


        try {
            List<Article> articles1 = sa.getAll();

            articles = FXCollections.observableArrayList(articles1);

            lv_articles.setItems(articles);

            lv_articles.setCellFactory(param -> new ListCell<Article>() {
                @Override
                protected void updateItem(Article article, boolean empty) {
                    super.updateItem(article, empty);

                    if (empty || article == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox();
                        hbox.setSpacing(10);

                        Text desc = new Text(article.getDescription());
                        Text prix = new Text(String.valueOf(article.getPrixUni()));
                        Text tva = new Text(String.valueOf(article.getTVA()));
                        Text qun = new Text(String.valueOf(article.getQuantity()));

                        desc.setWrappingWidth(60);
                        prix.setWrappingWidth(40);
                        tva.setWrappingWidth(30);
                        qun.setWrappingWidth(20);

                        TextField qu = new TextField();
                        qu.setPrefWidth(30);
                        qu.setPromptText("Qté");

                        // Associer le champ de texte à l'article dans la Map
                        articleQuantities.put(article, qu);


                        qu.textProperty().addListener((observable, oldValue, newValue) -> calculerTotal());


                        hbox.getChildren().addAll(desc, prix, tva, qun, qu);
                        setGraphic(hbox);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void rechercherFacture() {
        String keyword = tf_recherche.getText().toLowerCase().trim();
        List<Facture> filteredList = factures.stream()
                .filter(f -> f.getNum().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
        tv_facture.setItems(FXCollections.observableArrayList(filteredList));
    }
    @FXML
    void rechercherArticle() {
        String keyword = tf_recherchearticle.getText().toLowerCase().trim();

        List<Article> filteredList = articles.stream()
                .filter(a -> a.getDescription() != null && a.getDescription().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        // Update the TableView with the filtered list
        lv_articles.setItems(FXCollections.observableArrayList(filteredList));
    }
    @FXML
    void convertirDevise() {
        try {
            double montant;

            if (!tf_montant.getText().isEmpty()) {
                montant = Double.parseDouble(tf_montant.getText());
                tf_montant.clear();
            } else {
                montant = Double.parseDouble(tf_total.getText());
            }            String devise = cb_devise.getValue();
            tf_total.clear();
            if (devise == null) {
                lbl_resultat.setText("Sélectionnez une devise !");
                return;
            }
            double taux = getExchangeRate(devise);
            lbl_resultat.setText(String.format("%.2f TND = %.2f %s", montant, montant * taux, devise));
        } catch (NumberFormatException e) {
            lbl_resultat.setText("Montant invalide !");
        } catch (IOException e) {
            lbl_resultat.setText("Erreur API !");
        }
    }

    private double getExchangeRate(String currency) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        Scanner scanner = new Scanner(conn.getInputStream());
        String json = scanner.useDelimiter("\\A").next();
        scanner.close();

        JSONObject rates = new JSONObject(json).getJSONObject("rates");
        return rates.getDouble(currency) / rates.getDouble("TND");
    }

    @FXML
    void Ajout(ActionEvent event) {
        // Vérifier si tous les champs sont remplis
       if (dp_date.getValue() == null || cb_typefact.getValue() == null || tf_num.getText().isEmpty() || tf_total.getText().isEmpty()) {
            afficherAlerte("Erreur", "Veuillez remplir tous les champs !");
            return;
        }


        Facture f = new Facture();
        f.setDate(Date.valueOf(dp_date.getValue()));
        f.setTypeFact(cb_typefact.getValue());
        f.setNum(tf_num.getText());



        // Vérification du champ "total"

            f.setTotal(Double.parseDouble(tf_total.getText()));

        // Associer un client/fournisseur par défaut ou récupéré
        CLFr cl = getSelectedClientOrFournisseur();
        if (cl == null) {
            afficherAlerte("Erreur", "Aucun client ou fournisseur sélectionné !");
            return;
        }
        f.setClFr(cl);

        // Associer un paiement par défaut ou récupéré
        Paiement p = getSelectedPaiement();
        if (p == null) {
            afficherAlerte("Erreur", "Aucun paiement sélectionné !");
            return;
        }
        f.setPaiement(p);

        // Tentative d'ajout en base de données
        try {
            ps.add(f);  // Ajout de la facture dans la base de données
            factures.add(f);  // Ajout de la facture à la liste observable
            tv_facture.setItems(factures);  // Met à jour les items de la TableView
            tv_facture.refresh();  // Rafraîchit la TableView pour refléter le nouvel élément
            tf_total.clear();  // Réinitialise le champ total
            afficherAlerte("Succès", "Facture ajoutée avec succès !");
        } catch (Exception e) {
            afficherAlerte("Erreur", "Échec de l'ajout de la facture : " + e.getMessage());
        }
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private CLFr getSelectedClientOrFournisseur() {
        // Ici, on peut récupérer un client à partir d'un ComboBox ou autre méthode
        // return cb_clientFournisseur.getValue();

        // Sinon, assigner un client par défaut (doit exister en BD)
        return new CLFr(1, "Client par défaut", null, null, null, null, null, null, null);
    }

    private Paiement getSelectedPaiement() {
        // Ici, on peut récupérer un mode de paiement à partir d'un ComboBox
        // return cb_paiement.getValue();

        // Sinon, assigner un paiement par défaut
        return new Paiement(1, null, null, null, null, 0);
    }


    @FXML
    void Supp(ActionEvent event) {
        Facture selectedFacture = tv_facture.getSelectionModel().getSelectedItem();
        if (selectedFacture == null) {
            System.out.println("Veuillez sélectionner une facture à supprimer.");
            return;
        }
        try {
            ps.delete(selectedFacture);
            factures.remove(selectedFacture);
            tv_facture.refresh();
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer cette facture ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                ps.delete(selectedFacture);
                factures.remove(selectedFacture);
                tv_facture.refresh();
            }
        });

    }

    public void NavModif(ActionEvent actionEvent) {
        try {
            // Load the addEmploye.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/ModifFacture.fxml"));
            Parent addEmployeView = loader.load();

            // Replace the current content of the mainPane with the addEmployeView
            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void ChooseLine(MouseEvent event) {
        articleSelectionne.clear();
        List<Article> selec = lv_articles.getSelectionModel().getSelectedItems().stream().toList();
        articleSelectionne.addAll(selec);
        System.out.println(articleSelectionne);

        String selectedArticles = articleSelectionne.stream()
                .map(Article::getDescription)
                .collect(Collectors.joining("\n "));

        lbl_article.setText("Articles sélectionnés :\n" + selectedArticles);


        // Calculer le total après la sélection des articles
        calculerTotal();
    }

    @FXML
    void calculerTotal() {
        double total = 0;

        for (Article article : lv_articles.getItems()) {
            TextField qu = articleQuantities.get(article); // Récupérer le TextField associé à l'article
            if (qu != null) {
                try {
                    int quantite = Integer.parseInt(qu.getText().trim()); // Lire la quantité
                    double prixHT = article.getPrixUni() * quantite;
                    double prixTTC = prixHT + (prixHT * article.getTVA() / 100);
                    total += prixTTC;
                } catch (NumberFormatException e) {
                    System.err.println("Quantité invalide pour " + article.getDescription());
                }
            }
        }

        tf_total.setText(String.valueOf(total)); // Met à jour le champ total
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
}