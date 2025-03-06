package tn.esprit.powerHR.services.DemRepQuest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;

public class ApiService {


    public String Response (Date dDebut, Date dFin) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://date-calculator-api-apiverve.p.rapidapi.com/v1/datecalculator?start=" + dDebut + "&end=" + dFin ))
                .header("x-rapidapi-key", "e2fbd18c3amsh7eac53cba110a34p19a497jsnac5c8d1eb3b5")
                .header("x-rapidapi-host", "date-calculator-api-apiverve.p.rapidapi.com")
                .header("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
          //  System.out.println(response.body());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());
            String days = rootNode.path("data").path("days").asText();

            return days;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
