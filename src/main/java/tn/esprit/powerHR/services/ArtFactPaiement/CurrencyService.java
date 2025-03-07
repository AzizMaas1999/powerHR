package tn.esprit.powerHR.services.ArtFactPaiement;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CurrencyService {
    private static final String API_URL = "https://openexchangerates.org/api/latest.json";
    private static final String APP_ID = "TON_APP_ID"; // Remplace avec ta clé API

    public static double getExchangeRate(String from, String to) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "?app_id=" + APP_ID))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());

            double rateFrom = rootNode.path("rates").path(from).asDouble();
            double rateTo = rootNode.path("rates").path(to).asDouble();

            return rateTo / rateFrom;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
