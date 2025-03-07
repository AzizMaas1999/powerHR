package tn.esprit.powerHR.controllers.ArtFactPaiement;

import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import javafx.application.Platform;
import javafx.scene.control.Label;

import static spark.Spark.*;

public class WebhookServer {

    public static final String STRIPE_SECRET_KEY = "sk_test_51Qz88XLp4Kjx6gr23O7wtIFaoP3BBRenSyXexAdhGLyHnif5lnEcHxlunX3CvAxv5GktelCZ708X32ImQipQGkt700xzJpIQ48";  // Remplacez par votre clé API secrète
    public static final String STRIPE_ENDPOINT_SECRET = "whsec_..."; // Remplacez par votre secret webhook
    public static Label lblMessage; // Label JavaFX pour afficher le message

    public static void main(String[] args) {
        // Configurer Spark pour écouter les webhooks
        post("/webhook", (req, res) -> {
            String payload = req.body();
            String sigHeader = req.headers("Stripe-Signature");

            // Vérification du webhook
            try {
                Event event = Webhook.constructEvent(payload, sigHeader, STRIPE_ENDPOINT_SECRET);

                // Traitement selon l'événement
                if ("payment_intent.succeeded".equals(event.getType())) {
                    afficherMessagePaiement("Paiement réussi !");
                } else if ("payment_intent.failed".equals(event.getType())) {
                    afficherMessagePaiement("Paiement échoué !");
                }

                res.status(200); // Accuser réception de l'événement
                return "Événement traité avec succès";
            } catch (StripeException e) {
                res.status(400);
                return "Erreur de traitement de l'événement : " + e.getMessage();
            }
        });
    }

    public static void afficherMessagePaiement(String message) {
        // Cette méthode est appelée après la confirmation du paiement
        Platform.runLater(() -> {
            lblMessage.setText(message); // Met à jour le label avec le message de statut de paiement
        });
    }
}
