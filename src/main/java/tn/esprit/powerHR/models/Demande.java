package tn.esprit.powerHR.models;

import javafx.collections.ObservableArray;

import java.sql.Date;

public class Demande {
    private int id;
    private Date dateCreation;
    private String type, cause;
    private Date dateDebut, dateFin;
    private Employe employe;
    private String status;
    private float salaire;

    public Demande() {
    }

    public Demande(int id, Date dateCreation, String type, String cause, Date dateDebut, Date dateFin, Employe employe, String status, float salaire) {
        this.id = id;
        this.dateCreation = dateCreation;
        this.type = type;
        this.cause = cause;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.employe = employe;
        this.status = status;
        this.salaire = salaire;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", dateCreation=" + dateCreation +
                ", type='" + type + '\'' +
                ", cause='" + cause + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", employe=" + employe +
                ", status='" + status + '\'' +
                ", salaire=" + salaire +
                '}';
    }
}