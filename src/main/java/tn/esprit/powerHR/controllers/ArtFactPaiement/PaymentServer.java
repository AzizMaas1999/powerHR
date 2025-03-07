package tn.esprit.powerHR.controllers.ArtFactPaiement;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PaymentServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Gérer la page de succès
        server.createContext("/succes", new SuccessHandler());

        // Gérer la page d'annulation
        server.createContext("/cancel", new CancelHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("Serveur démarré sur http://localhost:8080");
    }

    // Handler pour la page de succès
    static class SuccessHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            byte[] response = Files.readAllBytes(Paths.get("src/main/resources/ArtFactPaiement/static/success.html"));
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }
    }

    // Handler pour la page d'annulation
    static class CancelHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            byte[] response = Files.readAllBytes(Paths.get("src/main/resources/ArtFactPaiement/static/cancel.html"));
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }
    }
}
