package tn.esprit.powerHR.models.User;

import tn.esprit.powerHR.models.Entreprise;

public class Candidat {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String cvPdfUrl;
    private String filePath;
    private Entreprise entreprise;

    public Candidat() {}

    public Candidat(int id, String nom, String prenom, String email, String telephone, String cvPdfUrl, Entreprise entreprise) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.cvPdfUrl = cvPdfUrl;
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

    public String getCvPdfUrl() {
        return cvPdfUrl;
    }

    public void setCvPdfUrl(String cvPdfUrl) {
        this.cvPdfUrl = cvPdfUrl;
    }
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
                ", cvPdfUrl='" + cvPdfUrl + '\'' +
                ", entreprise=" + entreprise +
                '}';
    }
}
