package tn.esprit.powerHR.models;

import java.sql.Time;
import java.sql.Date;

public class Pointage {
    private int id,employe_id;
    private Date date;
    private Time heureEntree;
    private Time heureSortie;

    public Pointage() {}

    public Pointage(int id, int employe_id, Date date, Time heureEntree, Time heureSortie) {
        this.id = id;
        this.employe_id = employe_id;
        this.date = date;
        this.heureEntree = heureEntree;
        this.heureSortie = heureSortie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmploye_id() {
        return employe_id;
    }

    public void setEmploye_id(int employe_id) {
        this.employe_id = employe_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeureEntree() {
        return heureEntree;
    }

    public void setHeureEntree(Time heureEntree) {
        this.heureEntree = heureEntree;
    }

    public Time getHeureSortie() {
        return heureSortie;
    }

    public void setHeureSortie(Time heureSortie) {
        this.heureSortie = heureSortie;
    }

    @Override
    public String toString() {
        return "Pointage{" +
                "id=" + id +
                ", employe_id=" + employe_id +
                ", date=" + date +
                ", heureEntree=" + heureEntree +
                ", heureSortie=" + heureSortie +
                '}';
    }
}
