package tn.esprit.powerHR.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Questionnaire {
    private int id;
    private Date dateCreation;
    private String objet;
    private String description;
    private List<RepQuestionnaire> reponses;
    private Employe employe;


    public Questionnaire(int id, Date dateCreation, String objet, String description, List<RepQuestionnaire> reponses) {
        this.id = id;
        this.dateCreation = dateCreation;
        this.objet = objet;
        this.description = description;
        this.reponses = new ArrayList<>();

    }
    public Questionnaire() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RepQuestionnaire> getReponses() {
        return reponses;
    }

    public void setReponses(List<RepQuestionnaire> reponses) {
        this.reponses = reponses;
    }
    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
                "id=" + id +
                ", dateCreation=" + dateCreation +
                ", objet='" + objet + '\'' +
                ", description='" + description + '\'' +
                ", reponses=" + reponses +
                ", employe=" + employe +
                '}';
    }
}



