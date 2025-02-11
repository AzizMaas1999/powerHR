package tn.esprit.powerHR.test;

import tn.esprit.powerHR.models.DemandeConge;
import tn.esprit.powerHR.models.DemandeSalaire;
import tn.esprit.powerHR.models.HistoriqueDemande;
import tn.esprit.powerHR.services.DemandeCongeService;
import tn.esprit.powerHR.services.DemandeSalaireService;
import tn.esprit.powerHR.services.HistoriqueDemandeService;
import tn.esprit.powerHR.utils.MyDataBase;
import java.sql.Date;


public class Main {
    public static void main(String[] args) {
        MyDataBase.getInstance();
        //ajouter demandes

//        DemandeSalaireService salaireService = new DemandeSalaireService();
//        DemandeSalaire demande = new DemandeSalaire(0 ,1, "Augmentation Salaire", 8000);
//        salaireService.add(demande);
//
//
//        DemandeCongeService service = new DemandeCongeService();
//        Date dateDebut = Date.valueOf("2025-03-01");
//        Date dateFin = Date.valueOf("2025-03-15");
//        DemandeConge demandes = new DemandeConge(0,1, "Conges", "hakekda", dateDebut, dateFin);
//        service.add(demandes);

       //update

//        Date nouvelleDateDebut = Date.valueOf("2017-04-01");
//        Date nouvelleDateFin = Date.valueOf("2019-03-15");
//        DemandeConge demandeMiseAJour = new DemandeConge(17, 1, "Conges", "Mals", nouvelleDateDebut, nouvelleDateFin);
//
//        service.update(demandeMiseAJour);
//


//}
//        //getall

//         System.out.println("Liste des demandes de salaire : " + salaireService.getAll());
//
//         System.out.println(" Liste des demandes de congé : " + service.getAll());}}


        //supp conge

//        DemandeConge demande = new DemandeConge();
//        demande.setId(8);
//        service.delete(demande);


//
//        // Ajouter un his

//        HistoriqueDemandeService service = new HistoriqueDemandeService();
//
//        HistoriqueDemande demande = new HistoriqueDemande(0, 1, 15, "Raisons personnelles", "Refuser");
//        service.add(demande);



        //update
//        HistoriqueDemande demandeToUpdate = new HistoriqueDemande(3, 1, 8, "Raisons professionnelles", "Valider");
//        service.update(demandeToUpdate);


        //supp his
//        int iddel = 2;
//        HistoriqueDemande demandeToDelete = new HistoriqueDemande();
//        demandeToDelete.setId(iddel);
//        service.delete(demandeToDelete);
//        System.out.println("Demande supprimée");

        //getall
//        System.out.println("Liste des demandes : " + service.getAll());
        }}

