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
import tn.esprit.powerHR.models.ArtFactPaiement.Facture;
import tn.esprit.powerHR.services.ArtFactPaiement.ServiceArticle;
import tn.esprit.powerHR.services.ArtFactPaiement.ServiceFacture;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ModifFactureController {

    @FXML
    private Button bt_ajouterfacture, bt_modiferfacture, bt_suppfacture;

    @FXML
    private ComboBox<String> cb_typefact;

    @FXML
    private DatePicker dp_date;

    @FXML
    private TextField tf_num, tf_total, tf_recherche;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView<Facture> tv_facture;

    @FXML
    private TableColumn<Facture, String> col_num, col_type;

    @FXML
    private TableColumn<Facture, Date> col_date;

    @FXML
    private TableColumn<Facture, Float> col_total;

    private final ServiceFacture ps = new ServiceFacture();
    private final ServiceArticle sa = new ServiceArticle();
    private ObservableList<Facture> factures = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurer les colonnes
        col_num.setCellValueFactory(new PropertyValueFactory<>("num"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("typeFact"));

        // Charger les factures
        factures.setAll(ps.getAll());
        tv_facture.setItems(factures);

        // Initialisation des types de facture
        cb_typefact.setItems(FXCollections.observableArrayList("Facture", "Avoir"));

        // Recherche dynamique
        tf_recherche.textProperty().addListener((observable, oldValue, newValue) -> rechercherFacture());
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
    void Supp(ActionEvent event) {
        Facture selectedFacture = tv_facture.getSelectionModel().getSelectedItem();
        if (selectedFacture == null) {
            showAlert(Alert.AlertType.WARNING, "Suppression", "Veuillez sélectionner une facture à supprimer.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer cette facture ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                ps.delete(selectedFacture);
                factures.remove(selectedFacture);
                tv_facture.refresh();
                showAlert(Alert.AlertType.INFORMATION, "Suppression", "Facture supprimée avec succès !");
            }
        });
    }

    @FXML
    void Modif(ActionEvent event) {
        Facture factureSelectionnee = tv_facture.getSelectionModel().getSelectedItem();

        if (factureSelectionnee == null) {
            showAlert(Alert.AlertType.WARNING, "Modification", "Veuillez sélectionner une facture à modifier.");
            return;
        }

        if (dp_date.getValue() == null || cb_typefact.getValue() == null || tf_num.getText().isEmpty() || tf_total.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Modification", "Tous les champs sont obligatoires !");
            return;
        }

        factureSelectionnee.setDate(Date.valueOf(dp_date.getValue()));
        factureSelectionnee.setTypeFact(cb_typefact.getValue());
        factureSelectionnee.setNum(tf_num.getText());

        try {
            factureSelectionnee.setTotal(Float.parseFloat(tf_total.getText()));
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer un nombre valide pour le total.");
            return;
        }

        try {
            ps.update(factureSelectionnee);

            // Supprimer et réajouter pour forcer la mise à jour
            factures.remove(factureSelectionnee);
            factures.add(factureSelectionnee);

            tv_facture.refresh();
            showAlert(Alert.AlertType.INFORMATION, "Modification", "Facture modifiée avec succès !");
            resetForm();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification de la facture.");
            e.printStackTrace();
        }
    }


    @FXML
    void ChooseLine(MouseEvent event) {
        Facture factureSelectionnee = tv_facture.getSelectionModel().getSelectedItem();

        if (factureSelectionnee != null) {
            System.out.println("Facture sélectionnée : " + factureSelectionnee.getNum()); // Vérification
            dp_date.setValue(factureSelectionnee.getDate().toLocalDate());
            cb_typefact.setValue(factureSelectionnee.getTypeFact());
            tf_num.setText(factureSelectionnee.getNum());
            tf_total.setText(String.valueOf(factureSelectionnee.getTotal()));
        } else {
            System.out.println("Aucune facture sélectionnée !");
        }
    }


    @FXML
    public void Retour(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArtFactPaiement/FactArtPaieHome.fxml"));
            Parent homeView = loader.load();
            mainPane.getChildren().setAll(homeView);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la page.");
        }
    }

    private void resetForm() {
        dp_date.setValue(null);
        cb_typefact.setValue(null);
        tf_num.clear();
        tf_total.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void NavModif(ActionEvent actionEvent) {
    }

    public void Ajout(ActionEvent actionEvent) {
    }
}
