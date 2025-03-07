package tn.esprit.powerHR.controllers.ArtFactPaiement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.powerHR.models.ArtFactPaiement.Article;
import tn.esprit.powerHR.services.ArtFactPaiement.ServiceArticle;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ModifArticleController {


    @FXML
    private Button bt_modiferarticle;



    @FXML
    private AnchorPane mainPane;


    @FXML
    private TableColumn<?, ?> col_description;

    @FXML
    private TableColumn<?, ?> col_prixuni;

    @FXML
    private TableColumn<?, ?> col_quantity;

    @FXML
    private TableColumn<?, ?> col_tva;


    @FXML
    private TableView<Article> tv_article;

    @FXML
    private TextField tf_description;

    @FXML
    private TextField tf_prixuni;

    @FXML
    private TextField tf_quantity;


    @FXML
    private TextField tf_recherche;

    @FXML
    private TextField tf_tva;
    private Article p;
    public void setListArticle(Article p) {
        this.p = p;
        System.out.println("Received Id: " + p); // Debugging
    }
    public Article getListArticle() {
        return p;
    }

    private  ServiceArticle serviceArticle = new ServiceArticle(); // Déclaration au niveau de la classe

    private List<Article> articles = FXCollections.observableArrayList(); // Liste des articles

    @FXML
    public void initialize() {
        try {
            // Récupérer la liste des articles depuis la base de données
            articles = serviceArticle.getAll();

            // Convertir la liste en ObservableList
            ObservableList<Article> observableList = FXCollections.observableArrayList(articles);

            // Lier la liste au TableView
            tv_article.setItems(observableList);

            // Configuration des colonnes
            col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
            col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            col_prixuni.setCellValueFactory(new PropertyValueFactory<>("prixUni"));
            col_tva.setCellValueFactory(new PropertyValueFactory<>("TVA"));

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des articles : " + e.getMessage());
            e.printStackTrace();
        }

        // Ajout du listener pour la recherche
        tf_recherche.textProperty().addListener((observable, oldValue, newValue) -> rechercherArticle());
    }

    @FXML
    void rechercherArticle() {
        String keyword = tf_recherche.getText().toLowerCase().trim();

        // Filtrer la liste
        List<Article> filteredList = articles.stream()
                .filter(a -> a.getDescription() != null && a.getDescription().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        // Mettre à jour le TableView avec la liste filtrée
        tv_article.setItems(FXCollections.observableArrayList(filteredList));
    }


    @FXML
    void ChooseLine(MouseEvent event) {
        // Récupérer l'article sélectionné
        Article selectedArticle = tv_article.getSelectionModel().getSelectedItem();

        if (selectedArticle != null) {  // Vérification pour éviter une erreur
            // Afficher les informations de l'article dans les champs de texte
            tf_description.setText(selectedArticle.getDescription());
            tf_quantity.setText(String.valueOf(selectedArticle.getQuantity()));
            tf_prixuni.setText(String.valueOf(selectedArticle.getPrixUni()));
            tf_tva.setText(String.valueOf(selectedArticle.getTVA()));

            // Stocker l'article sélectionné pour modification
            setListArticle(selectedArticle);
        } else {
            System.out.println("Aucun article sélectionné !");
        }
    }



    @FXML
    void Modif(ActionEvent event) {
        ServiceArticle ps = new ServiceArticle();
        Article a = getListArticle();

        if (a == null) {
            System.out.println("Aucun article sélectionné !");
            return;
        }

        // Vérification que tous les champs sont remplis
        if (tf_description.getText().isEmpty() || tf_quantity.getText().isEmpty() ||
                tf_prixuni.getText().isEmpty() || tf_tva.getText().isEmpty()) {
            System.out.println("Erreur : Tous les champs doivent être remplis !");
            return;
        }

        try {
            // Mise à jour des valeurs de l'article sélectionné
            a.setDescription(tf_description.getText());
            a.setQuantity(Integer.parseInt(tf_quantity.getText()));
            a.setPrixUni(Float.parseFloat(tf_prixuni.getText()));
            a.setTVA(Float.parseFloat(tf_tva.getText()));

            // Mise à jour de l'article dans la base de données
            ps.update(a);

            // Rafraîchir la liste des articles après modification
            initialize();

            System.out.println("Article modifié avec succès !");
        } catch (NumberFormatException e) {
            System.out.println("Erreur : Veuillez entrer des valeurs valides pour les champs numériques.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }



    @FXML
    void NavAjout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ArtFactPaiement/AjoutArticle.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage Stage = new Stage();
            Stage.setTitle("Modifier Article");
            Stage.setScene(scene);
            Stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void Retour(MouseEvent mouseEvent) {
        try {
            // Load the addEmploye.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/AjoutArticle.fxml"));
            Parent addEmployeView = loader.load();

            // Replace the current content of the mainPane with the addEmployeView
            mainPane.getChildren().setAll(addEmployeView);
        } catch (IOException e) {
            System.err.println("Error loading addEmploye.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
