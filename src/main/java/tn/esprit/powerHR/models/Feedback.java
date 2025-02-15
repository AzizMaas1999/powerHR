package tn.esprit.powerHR.models;
import java.sql.Date;

public class Feedback {
    private int id;
    private int id_clfr;
    private Date dateCreation;
    private String type;
    private String description;

    public Feedback() {
    }

    public Feedback(int id, int id_clfr, Date dateCreation, String type, String description) {
        this.id = id;
        this.id_clfr = id_clfr;
        this.dateCreation = dateCreation;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_clfr() {
        return id_clfr;
    }

    public void setId_clfr(int id_clfr) {
        this.id_clfr = id_clfr;
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
                ", utilisateur_Id=" + id_clfr +
                ", dateCreation=" + dateCreation +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
