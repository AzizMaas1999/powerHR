package tn.esprit.powerHR.services.PaiePointage;


import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.models.User.FicheEmploye;
import tn.esprit.powerHR.services.User.ServiceFicheEmploye;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ServiceApi {

    public ServiceApi() {
    }

    public void response(List<Employe> employes) {
        ServiceFicheEmploye serviceFicheEmploye = new ServiceFicheEmploye();
        List<String> emails = new ArrayList<>();
        for (Employe employe : employes) {
            emails.add(serviceFicheEmploye.getAll().stream()
                    .filter(f -> f.getEmploye().getId() == employe.getId())
                    .map(FicheEmploye::getEmail)
                    .findFirst()
                    .get());
            System.out.println(emails);
        }

        String emailListJson = emails.stream()
                .map(email -> "\"" + email + "\"")
                .collect(Collectors.joining(", "));
        System.out.println(emails);
        String body = "Nous vous informons que les salaires du mois de " + LocalDate.now().getMonth().getDisplayName(TextStyle.FULL,Locale.FRENCH).toUpperCase() + " ont été transmis et devraient être disponibles sur vos comptes bancaires sous peu.\\n" +
                "Pour toute question ou en cas de problème, n’hésitez pas à contacter le service des ressources humaines.\\n" +
                "Cordialement,";


                HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://send-bulk-emails.p.rapidapi.com/api/send/bulk/emails"))
                .header("x-rapidapi-key", "63ce630824msh737b54099b38971p129c30jsn41e9312fe9bd")
                .header("x-rapidapi-host", "send-bulk-emails.p.rapidapi.com")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\n" +
                        "  \"subject\": \"Confirmation de Transmission des Salaires\",\n" +
                        "  \"from\": \"humanressources@powerhr.tn\",\n" +
                        "  \"to\": [\n" +
                        emailListJson +
                        "  ],\n" +
                        "  \"senders_name\": \"PowerHR\",\n" +
                        "  \"body\": \"" +
                        body +
                        "\"\n" +
                        "}"))

		.build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
