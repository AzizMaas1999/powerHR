package tn.esprit.powerHR.models.ClfrFeedback;

import tn.esprit.powerHR.models.ArtFactPaiement.Facture;
import tn.esprit.powerHR.models.User.*;

import java.util.ArrayList;
import java.util.List;

public class CLFr {
    private int id;
    private String nom;
    private String matriculeFiscale;
    private String adresse;
    private String numTel;  // Ajoutez ce champ si ce n'est pas déjà fait
    private String type;
    private String photoPath;
    private List<Feedback> feedbacks = new ArrayList<>();
    private Employe employe ;
    private List<Facture> factures = new ArrayList<>();

    public CLFr() {}

    public CLFr(int id, String nom, String matriculeFiscale, String adresse, String numTel, String type, List<Feedback> feedbacks, Employe employe, List<Facture> factures) {
        this.id = id;
        this.nom = nom;
        this.matriculeFiscale = matriculeFiscale;
        this.adresse = adresse;
        this.numTel = numTel;
        this.type = type;
        this.feedbacks = feedbacks;
        this.employe = employe;
        this.factures = factures;
        this.photoPath = photoPath;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatriculeFiscale() {
        return matriculeFiscale;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Employe getEmploye() {
        return employe;
    }
    public String getPhotoPath() {
        return photoPath;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public List<Facture> getFactures() {
        return factures;
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
    }
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public String toString() {
        return nom + " | " + matriculeFiscale + " | " + adresse + " | " + numTel + " | " + type+ (photoPath != null ? " | Photo: " + photoPath : "");
    }
    public CLFr(String nom, String matriculeFiscale, String adresse, String numTel, String type) {
        this.nom = nom;
        this.matriculeFiscale = matriculeFiscale;
        this.adresse = adresse;
        this.numTel = numTel;
        this.type = type;
        this.photoPath = photoPath;
    }

}
