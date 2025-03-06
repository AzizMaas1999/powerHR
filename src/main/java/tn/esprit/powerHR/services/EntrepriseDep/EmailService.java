package tn.esprit.powerHr.services.EntrepriseDep;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class EmailService {
    private static final String API_KEY = "D+op30gmMeJ0geFc31syIg==Lq1jiRpNh69NYT9w";
    private static final String API_URL = "https://api.api-ninjas.com/v1/validateemail";

    public JSONObject validateEmail(String email) {
        try {
            System.out.println("Validating email: " + email); // Debug log
            
            HttpResponse<JsonNode> response = Unirest.get(API_URL)
                .header("X-Api-Key", API_KEY)
                .queryString("email", email)
                .asJson();

            System.out.println("Response status: " + response.getStatus()); // Debug log
            System.out.println("Response body: " + response.getBody()); // Debug log

            if (response.getStatus() == 200) {
                return response.getBody().getObject();
            } else {
                System.err.println("Error validating email. Status: " + response.getStatus());
                System.err.println("Response: " + response.getBody().toString());
                return null;
            }
        } catch (UnirestException e) {
            System.err.println("Failed to validate email: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
} 