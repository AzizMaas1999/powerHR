<<<<<<<< HEAD:src/main/java/tn/esprit/powerHR/test/DemRepQuest/MainFX.java
package tn.esprit.powerHR.test.DemRepQuest;
========
package tn.esprit.powerHR.test.User;
>>>>>>>> refs/remotes/origin/yassine:src/main/java/tn/esprit/powerHR/test/User/MainFX.java

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(
<<<<<<<< HEAD:src/main/java/tn/esprit/powerHR/test/DemRepQuest/MainFX.java
                getClass().getResource("/DemRepQuest/DemQuestRepHome.fxml"));
========
                getClass().getResource("/User/ManageFiche.fxml"));
>>>>>>>> refs/remotes/origin/yassine:src/main/java/tn/esprit/powerHR/test/User/MainFX.java
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Modif");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}