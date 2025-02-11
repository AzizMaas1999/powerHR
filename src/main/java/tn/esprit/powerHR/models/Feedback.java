package tn.esprit.powerHR.models;

public class Feedback {
    private int id;
    private int idClFr;
    private String dateCreation;
    private String type;
    private String description;

    // Constructeurs
    public Feedback() {}

    public Feedback(int idClFr, String dateCreation, String type, String description) {
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

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
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
                ", dateCreation='" + dateCreation + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
