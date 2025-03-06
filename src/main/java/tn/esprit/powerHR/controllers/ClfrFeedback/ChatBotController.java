package tn.esprit.powerHR.controllers.ClfrFeedback;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tn.esprit.powerHR.services.ClfrFeedback.OpenRouterService;

public class ChatBotController {
    @FXML
    private TextField inputField;
    @FXML
    private VBox chatVBox;

    // Méthode pour définir le contexte du ChatBot
    public void setContext(String contextMessage) {
        Label contextLabel = new Label("Assistant : " + contextMessage);
        contextLabel.getStyleClass().addAll("chat-bubble", "bot-bubble");
        chatVBox.getChildren().add(contextLabel);
    }

    // Gestionnaire de l'événement pour envoyer un message
    @FXML
    private void handleSend(ActionEvent event) {
        String userInput = inputField.getText();
        if (!userInput.trim().isEmpty()) {
            // Affichage du message de l'utilisateur
            Label userMessage = new Label("Vous : " + userInput);
            userMessage.getStyleClass().addAll("chat-bubble", "user-bubble");
            chatVBox.getChildren().add(userMessage);

            new Thread(() -> {
                try {
                    OpenRouterService openRouterService = new OpenRouterService();
                    // Obtenir la réponse de l'API
                    String botResponse = openRouterService.sendMessageToOpenRouter(userInput);
                    System.out.println("Bot response: " + botResponse);

                    // Vérifiez les conditions de réponse personnalisée (comme client/fournisseur)
                    String customResponse = generateCustomResponse(userInput);

                    // Si une réponse personnalisée existe, l'utiliser. Sinon, utiliser la réponse de l'API
                    String responseToDisplay = customResponse != null ? customResponse : botResponse;

                    // Nettoyer et assainir la réponse
                    String cleanedResponse = cleanUpResponse(responseToDisplay);
                    String sanitizedResponse = sanitizeResponse(cleanedResponse);

                    // Afficher la réponse dans l'interface utilisateur
                    Platform.runLater(() -> {
                        Label botMessage = new Label("Bot : " + sanitizedResponse);
                        botMessage.getStyleClass().addAll("chat-bubble", "bot-bubble");
                        chatVBox.getChildren().add(botMessage);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        Label errorMessage = new Label("Bot : Erreur lors de la communication avec l'API.");
                        chatVBox.getChildren().add(errorMessage);
                    });
                }
            }).start();
            inputField.clear();
        }
    }

    private String generateCustomResponse(String userInput) {
        // Gestion des salutations et autres réponses générales
        if (userInput.toLowerCase().contains("hi") || userInput.toLowerCase().contains("salut") || userInput.toLowerCase().contains("ça va")) {
            return "Salut ! Comment puis-je vous aider aujourd'hui ?";
        } else if (userInput.toLowerCase().contains("client") || userInput.toLowerCase().contains("fournisseur")) {
            return "Voici ce que vous pouvez faire avec les clients/fournisseurs : ajouter, modifier, supprimer. Si vous avez besoin d'aide, dites 'ajouter un client' ou 'supprimer un fournisseur'.";
        } else if (userInput.toLowerCase().contains("ajouter")) {
            return "Pour ajouter un client ou un fournisseur, remplissez tous les champs nécessaires dans le formulaire et cliquez sur 'Ajouter'.";
        } else if (userInput.toLowerCase().contains("supprimer")) {
            return "Pour supprimer un client ou un fournisseur, sélectionnez l'élément dans la liste et cliquez sur 'Supprimer'.";
        } else if (userInput.toLowerCase().contains("statistiques")) {
            return "Les statistiques montrent le nombre total de clients et de fournisseurs, ainsi que d'autres données pertinentes.";
        }
        // Si aucune réponse personnalisée n'est trouvée, renvoyer null pour que l'API prenne le relais
        return null;
    }




    // Méthode pour nettoyer la réponse
    private String cleanUpResponse(String response) {
        if (response != null) {
            response = response.replaceAll("[^\\x00-\\x7F]", "");
        }
        return response;
    }

    // Méthode pour assainir la réponse
    private String sanitizeResponse(String response) {
        if (response != null) {
            response = response.replaceAll("\\s+", " ").trim();
            response = response.replace("\n", " ");
        }
        return response;
    }
}

