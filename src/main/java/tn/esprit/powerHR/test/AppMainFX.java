package tn.esprit.powerHR.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppMainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Home.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.setTitle("powerHR");
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Icon.png")));
                primaryStage.show();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

        }

    }
