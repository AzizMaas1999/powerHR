package tn.esprit.powerHR.controllers.ArtFactPaiement;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PaiementController {

    @FXML
    private TextField tfMontant;

    @FXML
    private Label lblMessage;

    @FXML
    private Button btnPayer;

    public PaiementController() {
        // Utilise ta vraie clé secrète Stripe (NE JAMAIS partager en public !)
        Stripe.apiKey = "sk_test_51Qz88XLp4Kjx6gr23O7wtIFaoP3BBRenSyXexAdhGLyHnif5lnEcHxlunX3CvAxv5GktelCZ708X32ImQipQGkt700xzJpIQ48";
    }

    @FXML
    public void effectuerPaiement() {
        String montantStr = tfMontant.getText().trim();

        if (montantStr.isEmpty()) {
            lblMessage.setText("Veuillez entrer un montant !");
            return;
        }

        try {
            float montant = Float.parseFloat(montantStr);

            if (montant <= 0) {
                lblMessage.setText("Le montant doit être positif !");
                return;
            }

            // Création d'une session Stripe Checkout
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
//                    .setSuccessUrl("http://localhost:8000/success")  // URL après succès
//                    .setCancelUrl("http://localhost:8000/cancel")    // URL après annulation
                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("eur")
                                    .setUnitAmount((long) (montant * 100)) // Montant en centimes
                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName("Paiement Facture")
                                            .build())
                                    .build())
                            .build())
                    .build();

            Session session = Session.create(params);
            lblMessage.setText("Paiement en cours... Redirection vers Stripe");

            System.out.println("Lien de paiement : " + session.getUrl());

            // Ouvrir le lien de paiement dans le navigateur
            Desktop.getDesktop().browse(new URI(session.getUrl()));

        } catch (NumberFormatException e) {
            lblMessage.setText("Montant invalide, entrez un nombre !");
        } catch (StripeException | IOException | URISyntaxException e) {
            lblMessage.setText("Erreur lors du paiement !");
            e.printStackTrace();
        }
    }

    @FXML
    public void Retour(MouseEvent mouseEvent) {
        // TODO: Ajouter une action pour retourner à l'écran précédent
        System.out.println("Retour au menu");
    }
}
