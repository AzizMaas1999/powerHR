package tn.esprit.powerHR.services.ClfrFeedback;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import okhttp3.OkHttpClient;
public class OpenRouterService {
    private static final String API_KEY = "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";
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private final OkHttpClient client = new OkHttpClient();

    public OpenRouterService() {
    }

    public String sendMessageToOpenRouter(String userMessage) throws IOException {
        JsonObject jsonBody = new JsonObject();
        JsonArray messages = new JsonArray();
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", userMessage);
        messages.add(message);
        jsonBody.add("messages", messages);
        jsonBody.addProperty("model", "openai/gpt-3.5-turbo-0613");

        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        JsonElement response;
        InputStreamReader reader;

        if (responseCode != 200) {
            reader = new InputStreamReader(connection.getErrorStream());
            response = JsonParser.parseReader(reader);
            System.out.println("Error Response: " + response.toString());
            reader.close();
            throw new IOException("Failed to communicate with API. Response code: " + responseCode);
        } else {
            reader = new InputStreamReader(connection.getInputStream());
            response = JsonParser.parseReader(reader);
            System.out.println("Full Response: " + response.toString());
            if (!response.isJsonObject() || !response.getAsJsonObject().has("choices")) {
                reader.close();
                return "Réponse inattendue de l'API.";
            }
            JsonArray choicesArray = response.getAsJsonObject().getAsJsonArray("choices");
            String result;
            if (choicesArray == null || choicesArray.size() <= 0) {
                result = "Aucune réponse trouvée.";
            } else {
                JsonObject choicesObject = choicesArray.get(0).getAsJsonObject();
                if (choicesObject != null && choicesObject.has("message")) {
                    result = choicesObject.getAsJsonObject("message").get("content").getAsString();
                } else {
                    result = "Le champ message est manquant dans la réponse.";
                }
            }
            reader.close();
            return result;
        }
    }
}
