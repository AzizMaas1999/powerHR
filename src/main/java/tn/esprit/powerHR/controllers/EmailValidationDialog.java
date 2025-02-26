package tn.esprit.powerHR.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public class EmailValidationDialog extends Dialog<Void> {
    
    public EmailValidationDialog(JSONObject validationResult) {
        setTitle("Email Validation Results");
        setHeaderText(null);
        
        ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(closeButton);
        
        VBox content = new VBox(15);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setMinWidth(400);
        content.setStyle("-fx-background-color: white;");
        
        // Email is always present
        Label emailLabel = new Label("Email Address");
        emailLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        Label emailValue = new Label(validationResult.getString("email"));
        emailValue.setStyle("-fx-text-fill: #2c3e50;");
        
        // Validation Result is always present
        boolean isValid = validationResult.getBoolean("is_valid");
        Label validationLabel = new Label("Validation Result");
        validationLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        Label validationValue = new Label(isValid ? "VALID" : "INVALID");
        validationValue.setTextFill(isValid ? Color.GREEN : Color.RED);
        validationValue.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        content.getChildren().addAll(emailLabel, emailValue, new Separator(), 
                                   validationLabel, validationValue);
        
        // Only add these fields if email is valid
        if (isValid) {
            Label localPartLabel = new Label("Local Part");
            localPartLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
            Label localPartValue = new Label(validationResult.getString("local_part"));
            
            Label domainLabel = new Label("Domain");
            domainLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
            Label domainValue = new Label(validationResult.getString("domain"));
            
            content.getChildren().addAll(new Separator(), 
                                       localPartLabel, localPartValue,
                                       new Separator(),
                                       domainLabel, domainValue);
        }
        
        DialogPane dialogPane = getDialogPane();
        dialogPane.setContent(content);
        dialogPane.getStylesheets().add(getClass().getResource("/styles/dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-dialog");
    }
} 