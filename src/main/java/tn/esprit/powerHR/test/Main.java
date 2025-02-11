package tn.esprit.powerHR.test;

import tn.esprit.powerHR.utils.MyDataBase;
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.services.ServiceCLFr;
import tn.esprit.powerHR.models.Feedback;
import tn.esprit.powerHR.services.ServiceFeedback;

public class Main {
    public static void main(String[] args) {
        // Initialisation de la connexion à la base de données
        MyDataBase.getInstance();

        // Gestion des CLFr
        manageCLFr();

        // Gestion des Feedbacks
        manageFeedback();
    }

    private static void manageCLFr() {
        ServiceCLFr serviceCLFr = new ServiceCLFr();

        // 1. Création d'un nouveau CLFr avec une valeur unique pour matricule_fiscale
        CLFr newClfr = new CLFr();
        newClfr.setNom("Test Name");
        newClfr.setMatriculeFiscale("123456789" + System.currentTimeMillis()); // Génère une valeur unique
        newClfr.setAdresse("123 Test St");
        newClfr.setNumTel("1234567890");
        newClfr.setType("Test Type");

        // Ajout du CLFr
        serviceCLFr.add(newClfr);
        System.out.println("Après ajout du nouveau CLFr :");
        serviceCLFr.getAll().forEach(System.out::println);

        // 2. Mise à jour du CLFr existant (si disponible)
        if (!serviceCLFr.getAll().isEmpty()) {
            CLFr clfrToUpdate = serviceCLFr.getAll().get(0);
            clfrToUpdate.setNom("Entreprise B");
            clfrToUpdate.setNumTel("53703047");
            serviceCLFr.update(clfrToUpdate);
            System.out.println("Après mise à jour du CLFr :");
            serviceCLFr.getAll().forEach(System.out::println);
        } else {
            System.out.println("Aucun CLFr disponible pour la mise à jour.");
        }

        // 3. Suppression du CLFr (optionnel)
        // Attention : supprimer un CLFr associé à des Feedbacks peut poser des problèmes de cohérence
        // ou être bloqué par une contrainte de clé étrangère. Assurez-vous d'avoir une gestion adéquate (par exemple, cascade ou suppression manuelle des Feedbacks associés).
        serviceCLFr.delete(newClfr);
        System.out.println("Après suppression du CLFr :");
        serviceCLFr.getAll().forEach(System.out::println);
    }


    private static void manageFeedback() {
        ServiceFeedback serviceFeedback = new ServiceFeedback();
        ServiceCLFr serviceCLFr = new ServiceCLFr();

        // Récupération d'un CLFr existant pour associer le Feedback
        int clfrId = 0;
        if (!serviceCLFr.getAll().isEmpty()) {
            clfrId = serviceCLFr.getAll().get(0).getId();
        } else {
            System.out.println("Aucun CLFr disponible pour associer un Feedback.");
            return;
        }

        // 1. Création d'un nouveau Feedback associé à un CLFr existant
        Feedback newFeedback = new Feedback();
        newFeedback.setIdClFr(clfrId);
        newFeedback.setDateCreation(new java.sql.Date(System.currentTimeMillis()));
        newFeedback.setType("Service");
        newFeedback.setDescription("Excellent service!");
        serviceFeedback.add(newFeedback);
        System.out.println("Après ajout du nouveau Feedback :");
        serviceFeedback.getAll().forEach(System.out::println);

        // 2. Mise à jour du Feedback existant (si disponible)
        if (!serviceFeedback.getAll().isEmpty()) {
            Feedback feedbackToUpdate = serviceFeedback.getAll().get(0);
            feedbackToUpdate.setDescription("Good service, but could improve response time.");
            feedbackToUpdate.setType("Service Update");
            serviceFeedback.update(feedbackToUpdate);
            System.out.println("Après mise à jour du Feedback :");
            serviceFeedback.getAll().forEach(System.out::println);
        } else {
            System.out.println("Aucun Feedback disponible pour la mise à jour.");
        }

        // 3. Suppression du Feedback existant (si disponible)
        if (!serviceFeedback.getAll().isEmpty()) {
            Feedback feedbackToDelete = serviceFeedback.getAll().get(0);
            serviceFeedback.delete(feedbackToDelete);
            System.out.println("Après suppression du Feedback :");
            serviceFeedback.getAll().forEach(System.out::println);
        } else {
            System.out.println("Aucun Feedback disponible pour la suppression.");
        }
    }
}
