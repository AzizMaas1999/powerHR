package tn.esprit.powerHr.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {
    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        // Show Entreprises view by default
        showEntreprises();
    }

    @FXML
    private void showEntreprises() {
        loadView("/fxml/EntrepriseView.fxml");
    }

    @FXML
    private void showDepartements() {
        loadView("/fxml/DepartementView.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 