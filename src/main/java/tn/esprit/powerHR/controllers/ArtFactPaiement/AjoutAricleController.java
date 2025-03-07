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
import tn.esprit.powerHR.models.ArtFactPaiement.Article;
import tn.esprit.powerHR.models.ArtFactPaiement.Facture;
import tn.esprit.powerHR.services.ArtFactPaiement.ServiceArticle;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AjoutAricleController {

    @FXML
    private Button bt_ajouterarticle, bt_modiferarticle, bt_submit, bt_supparticle;

    @FXML
    private TextField tf_quantity, tf_recherche, tf_description, tf_prixuni, tf_tva;

    @FXML
    private TableView<Article> tv_article;

    @FXML
    private TableColumn<Article, String> col_description;

    @FXML
    private AnchorPane mainPane;


    @FXML
    private TableColumn<Article, Integer> col_quantity;

    @FXML
    private TableColumn<Article, Float> col_prixuni;

    @FXML
    private TableColumn<Article, Float> col_tva;

    private  ServiceArticle serviceArticle = new ServiceArticle(); // Service unique

    private ObservableList<Article> articles = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTable();
        loadArticles(); // Charger les articles à l'initialisation
        tf_recherche.textProperty().addListener((observable, oldValue, newValue) -> rechercherArticle());
    }

    private void setupTable() {
        // Setup columns directly with the getter methods from the Article class
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_prixuni.setCellValueFactory(new PropertyValueFactory<>("prixUni"));
        col_tva.setCellValueFactory(new PropertyValueFactory<>("TVA"));
    }

    private void loadArticles() {
        try {
            List<Article> list = serviceArticle.getAll();
            articles.setAll(list); // Stocker la liste des articles
            // Use stream to directly set the items for TableView
            tv_article.setItems(articles.stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
        } catch (Exception e) {
            System.out.println("Erreur de chargement des articles : " + e.getMessage());
        }
    }

    @FXML
    void rechercherArticle() {
        String keyword = tf_recherche.getText().toLowerCase().trim();

        // Use stream to filter the list based on the keyword
        List<Article> filteredList = articles.stream()
                .filter(a -> a.getDescription() != null && a.getDescription().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        // Update the TableView with the filtered list
        tv_article.setItems(FXCollections.observableArrayList(filteredList));
    }

    @FXML
    void Ajout(ActionEvent event) {
        Article a = new Article();
        a.setDescription(tf_description.getText());

        try {
            a.setQuantity(Integer.parseInt(tf_quantity.getText()));
            a.setPrixUni(Float.parseFloat(tf_prixuni.getText()));
            a.setTVA(Float.parseFloat(tf_tva.getText()));
        } catch (NumberFormatException e) {
            System.out.println("Erreur : Valeurs incorrectes !");
            return;
        }

        a.setFacture(new Facture(3, null, null, null, 0, null, null, null));

        try {
            serviceArticle.add(a);
            loadArticles(); // Recharger après ajout
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @FXML
    void Supp(ActionEvent event) {
        Article selectedArticle = tv_article.getSelectionModel().getSelectedItem();
        if (selectedArticle == null) {
            System.out.println("Veuillez sélectionner un article à supprimer.");
            return;
        }

        try {
            serviceArticle.delete(selectedArticle);
            loadArticles(); // Recharger après suppression
        } catch (Exception e) {
            System.out.println("Erreur de suppression : " + e.getMessage());
        }
    }

    public void NavModif(ActionEvent actionEvent) {
        try {
            // Load the addEmploye.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/ModifArticle.fxml"));
            Parent addEmployeView = loader.load();

            // Replace the current content of the mainPane with the addEmployeView
            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
        // Navigate to modification screen if needed
    }


    public void Retour(MouseEvent mouseEvent) {

        try {
            // Load the addEmploye.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FacturationHome.fxml"));
            Parent addEmployeView = loader.load();

            // Replace the current content of the mainPane with the addEmployeView
            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
