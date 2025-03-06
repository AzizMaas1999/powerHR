package tn.esprit.powerHR.models;

import tn.esprit.powerHR.models.EntrepriseDep.Entreprise;

import java.sql.Blob;

public class Candidat {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private Blob cvPdf;
    private Entreprise entreprise;

    public Candidat() {}

    public Candidat(int id, String nom, String prenom, String email, String telephone, Blob cvPdf, Entreprise entreprise) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.cvPdf = cvPdf;
        this.entreprise = entreprise;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Blob getCvPdf() {
        return cvPdf;
    }

    public void setCvPdf(Blob cvPdf) {
        this.cvPdf = cvPdf;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    @Override
    public String toString() {
        return "Candidat{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", cvPdf=" + cvPdf +
                ", entreprise=" + entreprise +
                '}';
    }
}
