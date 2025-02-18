package tn.esprit.powerHR.models;

public class CLFr {
    private int id;
    private String nom;
    private int matriculeFiscale; // Changement de String à int
    private String adresse;
    private int numtel; // Changement de String à int
    private String type;

    // Constructeurs
    public CLFr() {}

    public CLFr(String nom, int matriculeFiscale, String adresse, int numtel, String type) {
        this.nom = nom;
        this.matriculeFiscale = matriculeFiscale;
        this.adresse = adresse;
        this.numtel = numtel;
        this.type = type;
    }

    // Getters et Setters
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

    public int getMatriculeFiscale() {
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

    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CLFr{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", matriculeFiscale=" + matriculeFiscale + // Correction ici
                ", adresse='" + adresse + '\'' +
                ", numtel=" + numtel + // Correction ici
                ", type='" + type + '\'' +
                '}';
    }
}

