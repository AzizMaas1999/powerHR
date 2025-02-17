package tn.esprit.powerHR.models;

import java.util.ArrayList;
import java.util.List;

public class CLFr {
    private int id;
    private String nom;
    private String matriculeFiscale;
    private String adresse;
    private String numTel;  // Ajoutez ce champ si ce n'est pas déjà fait
    private String type;
    private List<Feedback> feedbacks = new ArrayList<>();

    public CLFr() {}

    public CLFr(int id, String nom, String matriculeFiscale, String adresse, String numTel, String type, List<Feedback> feedbacks) {
        this.id = id;
        this.nom = nom;
        this.matriculeFiscale = matriculeFiscale;
        this.adresse = adresse;
        this.numTel = numTel;
        this.type = type;
        this.feedbacks = feedbacks;
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

    @Override
    public String toString() {
        return "CLFr{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", matriculeFiscale='" + matriculeFiscale + '\'' +
                ", adresse='" + adresse + '\'' +
                ", numTel='" + numTel + '\'' +
                ", type='" + type + '\'' +
                ", feedbacks=" + feedbacks +
                '}';
    }
}
