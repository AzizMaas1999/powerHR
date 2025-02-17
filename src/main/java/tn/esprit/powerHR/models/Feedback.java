package tn.esprit.powerHR.models;

import java.sql.Date;

public class Feedback {
    private int id;
    private Date dateCreation; // Utilisation du type Date pour correspondre à 'date' dans la base
    private String type;
    private String description;
    private CLFr clfr ;

    // Constructeurs
    public Feedback() {}

    public Feedback(int id, Date dateCreation, String type, String description, CLFr clfr) {
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

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
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
        return "Feedback{" +
                "id=" + id +
                ", dateCreation=" + dateCreation +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", clfr=" + clfr +
                '}';
    }
}
