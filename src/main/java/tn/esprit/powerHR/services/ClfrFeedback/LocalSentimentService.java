package tn.esprit.powerHR.services.ClfrFeedback;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.json.JSONObject;

public class LocalSentimentService {
    private static final String API_URL = "http://localhost:5000/analyze";
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String analyserSentiment(String texte) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("text", texte);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
                    .timeout(Duration.ofSeconds(15))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 200) {
                return parseResponse(response.body());
            } else {
                return "Erreur API: " + response.statusCode();
            }
        } catch (Exception e) {
            return "Échec de l'analyse: " + e.getMessage();
        }
    }

    private String parseResponse(String jsonResponse) {
        JSONObject responseJson = new JSONObject(jsonResponse);
        String sentiment = responseJson.getString("sentiment");
        double score = responseJson.getDouble("score");

        String frenchSentiment = switch (sentiment.toLowerCase()) {
            case "positive" -> "Positif";
            case "negative" -> "Négatif";
            default -> "Neutre";
        };

        return String.format("%s (Confiance: %.2f)", frenchSentiment, score);
    }
}
