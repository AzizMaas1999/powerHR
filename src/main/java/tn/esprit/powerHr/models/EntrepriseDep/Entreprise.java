package tn.esprit.powerHr.models.EntrepriseDep;

public class Entreprise {
    private int id;
    private String nom;
    private String secteur;
    private String matriculeFiscale;
    private String phoneNumber;
    private boolean phoneVerified;

    // Constructor
    public Entreprise() {}

    public Entreprise(String nom, String secteur, String matriculeFiscale, String phoneNumber) {
        this.nom = nom;
        this.secteur = secteur;
        this.matriculeFiscale = matriculeFiscale;
        this.phoneNumber = phoneNumber;
        this.phoneVerified = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getSecteur() { return secteur; }
    public void setSecteur(String secteur) { this.secteur = secteur; }
    public String getMatriculeFiscale() { return matriculeFiscale; }
    public void setMatriculeFiscale(String matriculeFiscale) { this.matriculeFiscale = matriculeFiscale; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public boolean isPhoneVerified() { return phoneVerified; }
    public void setPhoneVerified(boolean phoneVerified) { this.phoneVerified = phoneVerified; }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", nom, secteur, matriculeFiscale);
    }
} 