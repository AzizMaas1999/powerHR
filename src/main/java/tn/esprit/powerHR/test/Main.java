package tn.esprit.powerHR.test;

import tn.esprit.powerHR.models.Article;
import tn.esprit.powerHR.models.Facture;
import tn.esprit.powerHR.models.Paiement;
import tn.esprit.powerHR.services.ServiceArticle;
import tn.esprit.powerHR.services.ServiceFacture;
import tn.esprit.powerHR.services.ServicePaiement;
import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Créer des instances des services
        ServiceArticle serviceArticle = new ServiceArticle();
        ServiceFacture serviceFacture = new ServiceFacture();
        ServicePaiement servicePaiement = new ServicePaiement();

        // Tester les opérations sur les articles
        System.out.println("=== TEST DES ARTICLES ===");

        // Ajout d'un article
//        Article article1 = new Article(1, 2, "ordinateur", 5, 1200.0, 0.19);
//        serviceArticle.add(article1);
//        System.out.println("Article 1 ajouté avec succès !");

//        // Afficher tous les articles
//        List<Article> articles = serviceArticle.getAll();
//        System.out.println("Liste des articles : " + articles);
//
//        // Mettre à jour un article
//        Article articleToUpdate = new Article(1, 1, "ordinateur mis à jour", 3, 900.0, 0.19);
//        serviceArticle.update(articleToUpdate);
//        System.out.println("Article mis à jour avec succès !");
//
//        // Afficher à nouveau la liste des articles après mise à jour
//        System.out.println("Liste des articles après mise à jour : " + serviceArticle.getAll());

//        // Supprimer un article
//        Article demande = new Article();
//        demande.setId(15);
//        serviceArticle.delete(demande);

//        // Afficher à nouveau la liste des articles après suppression
//        System.out.println("Liste des articles après suppression : " + serviceArticle.getAll());
//
//        // Tester les opérations sur les factures
//        System.out.println("\n=== TEST DES FACTURES ===");
//
//        // Ajout d'une facture
//        Facture facture1 = new Facture(1, 1, "Facture", new Date(System.currentTimeMillis()), "FAC-001", 5000.0);
//        serviceFacture.add(facture1);
//        System.out.println("Facture 1 ajoutée avec succès !");
////
//        // Afficher toutes les factures
//        List<Facture> factures = serviceFacture.getAll();
//        System.out.println("Liste des factures : " + factures);
//
//        // Mettre à jour une facture
//        Facture factureToUpdate = new Facture(2, 1, "Avoir", new Date(System.currentTimeMillis()), "AV-008", 4000.0);
//        serviceFacture.update(factureToUpdate);
//        System.out.println("Facture mise à jour avec succès !");
////
//        // Afficher à nouveau la liste des factures après mise à jour
//        System.out.println("Liste des factures après mise à jour : " + serviceFacture.getAll());
//
//        // Supprimer une facture
//       Facture f = new Facture();
//        f.setId(3);
//        serviceFacture.delete(f);
//
//        // Afficher à nouveau la liste des factures après suppression
//        System.out.println("Liste des factures après suppression : " + serviceFacture.getAll());
//
//        // Tester les opérations sur les paiements
//        System.out.println("\n=== TEST DES PAIEMENTS ===");
//
        // Ajout d'un paiement
//        Paiement paiement1 = new Paiement(1,2, new Date(System.currentTimeMillis()), "visa", "PAY-001", 5000.0);
//        servicePaiement.add(paiement1);
//        System.out.println("Paiement 1 ajouté avec succès !");
//
//        // Afficher tous les paiements
//        List<Paiement> paiements = servicePaiement.getAll();
//        System.out.println("Liste des paiements : " + paiements);

//        // Mettre à jour un paiement
//        Paiement paiementToUpdate = new Paiement(1, 2, new Date(System.currentTimeMillis()), "Visa", "PAY-002", 5000.0);
//        servicePaiement.update(paiementToUpdate);
//        System.out.println("Paiement mis à jour avec succès !");
//
//        // Afficher à nouveau la liste des paiements après mise à jour
//        System.out.println("Liste des paiements après mise à jour : " + servicePaiement.getAll());
//
        // Supprimer un paiement
//       Paiement p = new Paiement();
//        p.setId(3);
//        servicePaiement.delete(p);
//        System.out.println("Paiement Supprimer avec succès !");
//
//
//    }
    }}