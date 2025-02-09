package tn.esprit.powerHR.models;

public class Entreprise {
    private int id;
    private String nom;
    private String type;
    private String secteur;
    private String matriculeFiscale;

    // Constructor
    public Entreprise() {}

    public Entreprise(String nom, String type, String secteur, String matriculeFiscale) {
        this.nom = nom;
        this.type = type;
        this.secteur = secteur;
        this.matriculeFiscale = matriculeFiscale;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getSecteur() { return secteur; }
    public void setSecteur(String secteur) { this.secteur = secteur; }
    public String getMatriculeFiscale() { return matriculeFiscale; }
    public void setMatriculeFiscale(String matriculeFiscale) { this.matriculeFiscale = matriculeFiscale; }

    @Override
    public String toString() {
        return "Entreprise{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", secteur='" + secteur + '\'' +
                ", matriculeFiscale='" + matriculeFiscale + '\'' +
                '}';
    }
} 