package tn.esprit.powerHR.models;

import java.sql.Blob;

public class Candidat {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private Blob cvPdf;

    public Candidat() {}

    public Candidat(int id, String nom, String prenom, String email, String telephone, Blob cvPdf) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        setEmail(email);
        setTelephone(telephone);
        this.cvPdf = cvPdf;
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
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Nom cannot be null or empty");
        }
        this.nom = nom;
    }

    public String getPrenom() {
        if (prenom == null || prenom.trim().isEmpty()) {
            throw new IllegalArgumentException("Prenom cannot be null or empty");
        }
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        if (!telephone.matches("^\\+?[0-9]{10,15}$")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        this.telephone = telephone;
    }

    public Blob getCvPdf() {
        return cvPdf;
    }

    public void setCvPdf(Blob cvPdf) {
        this.cvPdf = cvPdf;
    }

    @Override
    public String toString() {
        return "Candidat{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", cvPdf=" + (cvPdf != null ? "Exists" : "No File") +
                '}';
    }
}
