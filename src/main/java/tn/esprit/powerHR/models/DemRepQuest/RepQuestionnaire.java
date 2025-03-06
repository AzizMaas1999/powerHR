package tn.esprit.powerHR.models.DemRepQuest;

import tn.esprit.powerHR.models.Employe;

import java.sql.Date;

public class RepQuestionnaire {
    private int id;
    private Date dateCreation;
    private String reponse;
    private Employe employe;
    private Questionnaire questionnaire;


    public RepQuestionnaire(int id,Date dateCreation,  String reponse, Employe employe ,Questionnaire questionnaire) {
        this.id = id;
        this.dateCreation = dateCreation;
        this.reponse = reponse;
        this.employe = employe;
        this.questionnaire = questionnaire;


    }
    public RepQuestionnaire() {}

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

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    @Override
    public String toString() {
        return "RepQuestionnaire{" +
                "id=" + id +
                ", dateCreation=" + dateCreation +
                ", reponse='" + reponse + '\'' +
                ", employe=" + employe +
                ", questionnaire=" + questionnaire +
                '}';
    }
}