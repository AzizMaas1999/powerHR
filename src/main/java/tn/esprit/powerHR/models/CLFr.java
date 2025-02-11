package tn.esprit.powerHR.models;

public class CLFr {
    private int id;
    private String nom;
    private String matriculeFiscale;
    private String adresse;
    private String numTel;  // Ajoutez ce champ si ce n'est pas déjà fait
    private String type;

    // Getter et Setter pour l'ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // Getters et Setters
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

    @Override
    public String toString() {
        return "CLFr{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", matriculeFiscale='" + matriculeFiscale + '\'' +
                ", adresse='" + adresse + '\'' +
                ", numTel='" + numTel + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
