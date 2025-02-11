package tn.esprit.powerHR.models;

import java.sql.Date;

public class Feedback {
    private int id;
    private int idClFr;
    private Date dateCreation; // Utilisation du type Date pour correspondre à 'date' dans la base
    private String type;
    private String description;

    // Constructeurs
    public Feedback() {}

    public Feedback(int idClFr, Date dateCreation, String type, String description) {
        this.idClFr = idClFr;
        this.dateCreation = dateCreation;
        this.type = type;
        this.description = description;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClFr() {
        return idClFr;
    }

    public void setIdClFr(int idClFr) {
        this.idClFr = idClFr;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", idClFr=" + idClFr +
                ", dateCreation=" + dateCreation +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
