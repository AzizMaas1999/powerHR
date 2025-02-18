package tn.esprit.powerHR.test;
import tn.esprit.powerHR.models.Demande;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.Questionnaire;
import tn.esprit.powerHR.models.RepQuestionnaire;
import tn.esprit.powerHR.services.DemandeService;
import tn.esprit.powerHR.services.ServiceQuestionnaire;

import tn.esprit.powerHR.services.ServiceRepQuestionnaire;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        MyDataBase.getInstance();

//        // Ajouter un questionnaire
//       Date dateCreation = Date.valueOf("2025-03-01");
      // List<RepQuestionnaire> reponses = new ArrayList<>();
//        Questionnaire q1 = new Questionnaire(0, dateCreation, "Satisfaction Employé", "Évaluation de la satisfaction au travail", reponses);
//        serviceQuestionnaire.add(q1);
//        System.out.println("Questionnaire ajouté !");
//    }}


        // Afficher tous les questionnaires
      //  System.out.println("Liste des demandes : " + serviceQuestionnaire.getAll());

        // Modifier le premier questionnaire
//        Date nouvelleDate = Date.valueOf("2017-04-01");
//        Questionnaire q = new Questionnaire(1, nouvelleDate, "dada", "da", reponses);
//        serviceQuestionnaire.update(q);
//       System.out.println("Questionnaire mis à jour !");}}
//
        // Supprimer le premier questionnaire
//      Questionnaire ques = new Questionnaire();
//        ques.setId(2);
//        serviceQuestionnaire.delete(ques);
//       System.out.println("Questionnaire supprimé !");
//    }
  //   ServiceRepQuestionnaire service = new ServiceRepQuestionnaire();
//
        // 1️⃣ Ajout d'une réponse
//        Employe employe = new Employe(2,null,null,null,null,null,null);
//        Questionnaire questionnaire = new Questionnaire(1,null,null,null,null);
//        RepQuestionnaire rep1 = new RepQuestionnaire(0,dateCreation ,"Très satisfait",employe,questionnaire);
//        service.add(rep1);
//        System.out.println("✅ Réponse ajoutée : " + rep1);


DemandeService demandeService = new DemandeService();


        Employe employe = new Employe(2,null,null,null,null,null,null);

// Ajout d'une demande de congé
        Date dateCreation = Date.valueOf("2025-02-18");
        Date dateDebut = Date.valueOf("2025-03-01");
        Date dateFin = Date.valueOf("2025-03-15");
        Demande demandeConge = new Demande(0, dateCreation, "Conges", "Besoin de repos", dateDebut, dateFin, employe, "Valider", 0);
        demandeService.add(demandeConge);

        // Mise à jour d'une demande existante (en supposant que l'ID 1 existe)
//        Date ndateCreation = Date.valueOf("2027-02-18");
//        Date nouvelleDateDebut = Date.valueOf("2028-04-01");
//        Date nouvelleDateFin = Date.valueOf("2028-04-15");
//        Demande demandeMiseAJour = new Demande(1, ndateCreation, "Conges", "Raison mise à jour", nouvelleDateDebut, nouvelleDateFin, null ,"En Attente", 0);
//        demandeService.update(demandeMiseAJour);

        // Affichage de toutes les demandes
       // System.out.println("Liste des demandes : " + demandeService.getAll());

        // Suppression d'une demande (en supposant que l'ID 2 existe)
//        Demande demandeASupprimer = new Demande();
//        demandeASupprimer.setId(2);
//        demandeService.delete(demandeASupprimer);
    }
}
