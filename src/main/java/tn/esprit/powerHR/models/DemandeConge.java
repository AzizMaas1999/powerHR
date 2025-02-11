package tn.esprit.powerHR.models;

import java.sql.Date;

public class DemandeConge {
    private int id, idEmploye;
    private String type, cause;
    private Date dateDebut, dateFin;

    public DemandeConge() {
    }

    public DemandeConge(int id, int idEmploye, String type, String cause, Date dateDebut, Date dateFin) {
        this.id = id;
        this.idEmploye = idEmploye;
        this.type = type;
        this.cause = cause;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
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

    public java.sql.Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public java.sql.Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    @Override
    public String toString() {
        return "DemandeConge{" +
                "id=" + id +
                ", idEmploye=" + idEmploye +
                ", type='" + type + '\'' +
                ", cause='" + cause + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
}