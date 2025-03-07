package tn.esprit.powerHR.controllers.ArtFactPaiement;

import static spark.Spark.*;

public class PaymentServer {
    public static void main(String[] args) {
        port(8080); // Définit le port

        get("/", (req, res) -> "<h1>Bienvenue !</h1><a href='/success'>Tester succès</a>");

        get("/success", (req, res) -> "<h1>Paiement réussi !</h1><a href='/'>Retour</a>");

        get("/cancel", (req, res) -> "<h1>Paiement annulé.</h1><a href='/'>Retour</a>");
    }
}

