package tn.esprit.powerHR.models.ClfrFeedback;

import java.sql.Timestamp;

public class Feedback {
    private int id;
    private Timestamp dateCreation;
    // Utilisation de Timestamp pour la date et l'heure
    private String type;
    private String description;
    private CLFr clfr;

    // Constructeurs
    public Feedback() {}

    public Feedback(int id, Timestamp dateCreation, String type, String description, CLFr clfr) {
        this.id = id;
        this.dateCreation = dateCreation;
        this.type = type;
        this.description = description;
        this.clfr = clfr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CLFr getClfr() {
        return clfr;
    }

    public void setClfr(CLFr clfr) {
        this.clfr = clfr;
    }

    @Override
    public String toString() {
        return type + " | " + description + " | " + dateCreation;
    }
}
