package tn.esprit.powerHr.controllers.EntrepriseDep;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import tn.esprit.powerHr.services.EmailService;

public class YourController {

    @FXML
    private TextField emailField;

    private final EmailService emailService = new EmailService();

    @FXML
    private void validateEmail() {
        String email = emailField.getText();
        if (email != null && !email.isEmpty()) {
            JSONObject validationResult = emailService.validateEmail(email);
            if (validationResult != null) {
                EmailValidationDialog dialog = new EmailValidationDialog(validationResult);
                dialog.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to validate email. Please try again.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter an email address.");
            alert.showAndWait();
        }
    }
} 