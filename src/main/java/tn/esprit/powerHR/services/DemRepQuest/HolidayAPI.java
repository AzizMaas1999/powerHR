package tn.esprit.powerHR.services.DemRepQuest;

import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tn.esprit.powerHR.models.DemRepQuest.Holiday;

import java.util.ArrayList;
import java.util.List;

public class HolidayAPI {

    private static final String API_KEY = "N7UBNmURg6UslVb8yYS2uw==o8Wub6wy1lBttEVO";
    private static final String API_URL = "https://api.api-ninjas.com/v1/holidays?country=Tunisia";

    public List<Holiday> getHolidays() {
        List<Holiday> holidaysList = new ArrayList<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("X-Api-Key", API_KEY);

            if (connection.getResponseCode() != 200) {
                System.out.println("Erreur HTTP : " + connection.getResponseCode());
                return holidaysList;
            }

            JsonNode holidays = new ObjectMapper().readTree(connection.getInputStream());
            holidays.forEach(holiday -> {

                holidaysList.add(new Holiday(holiday.path("name").asText(), holiday.path("date").asText()));
            });

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return holidaysList;
    }
}
