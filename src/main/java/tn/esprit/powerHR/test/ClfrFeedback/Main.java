package tn.esprit.powerHR.test.ClfrFeedback;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//public class Main {
  //  public static void main(String[] args) {
//        // Initialisation de la connexion à la base de données
//        MyDataBase.getInstance();
//
//        ServiceCLFr serviceCLFr = new ServiceCLFr();
//        Employe e = new Employe(1,"fdkbgkndfg","fdkbgkndfg","chargesRH",445.2,"123456789125","fdkbgkndfg");
//        CLFr c = new CLFr(1,"haifa","1518yh","rue hghghggh","+5154545","Client",null,e,null);
////        // 1. Création d'un nouveau CLFr
////        CLFr newClfr = new CLFr(10,"haifa","1518yh","rue hghghggh","+5154545","Client",null,e,null);
//
////        // Ajout du CLFr
////        serviceCLFr.add(newClfr);
////        System.out.println("Après ajout du nouveau CLFr :");
////        serviceCLFr.getAll().forEach(System.out::println);
////
////        // Modif
////            CLFr clfrToUpdate = new CLFr(10,"haifaa","555ff","rue chacha","+5154545","Fournisseur",null,e,null);
////            serviceCLFr.update(clfrToUpdate);
////            System.out.println("Après mise à jour du CLFr :");
////            serviceCLFr.getAll().forEach(System.out::println);
////
////        // 3. Suppression du CLFr
////        serviceCLFr.delete(newClfr);
////        System.out.println("Après suppression du CLFr :");
////        serviceCLFr.getAll().forEach(System.out::println);
//
//        ServiceFeedback serviceFeedback = new ServiceFeedback();
//        // 1. Création d'un nouveau Feedback
//        Date d = Date.valueOf("2025-10-19");
//        Feedback newFeedback = new Feedback(0,d,"ghbfgubdy","kjndfghdfg",c);
//
////        // Ajout du Feedback
////        serviceFeedback.add(newFeedback);
////        System.out.println("Après ajout du nouveau Feedback :");
////        serviceFeedback.getAll().forEach(System.out::println);
//
//        // Modif
//        Feedback feedbackToUpdate = new Feedback(4,d,"hhhhhhhh","aaaaaaaaaa",c);
////        serviceFeedback.update(feedbackToUpdate);
////        System.out.println("Après mise à jour du feedback :");
////        serviceFeedback.getAll().forEach(System.out::println);
//
////        // 3. Suppression du CLFr
////        serviceFeedback.delete(feedbackToUpdate);
////        System.out.println("Après suppression du feedback :");
////        serviceFeedback.getAll().forEach(System.out::println);
//    }
//
//    public static class MainApp extends Application {
//        @Override
//        public void start(Stage primaryStage) throws Exception {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controllers/views/AjouterFeedback.fxml"));
//            AnchorPane root = loader.load();
//            Scene scene = new Scene(root);
//            primaryStage.setTitle("Ajouter Feedback");
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        }
//
//        public static void main(String[] args) {
//            launch(args);
//        }

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ChatBot.fxml"));
        Scene scene = new Scene(root, 600, 400);

        // Appliquer le CSS
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setTitle("Chatbot Assistant");
        stage.setScene(scene);
        stage.show();
    }
}