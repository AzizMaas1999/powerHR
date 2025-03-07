package tn.esprit.powerHR.models.PaiePointage;

import java.sql.Time;
import java.sql.Date;

public class Pointage {
    private int id;
    private Date date;
    private Time heureEntree;
    private Time heureSortie;
    private Employe employe;
    private Paie paie;

    public Pointage() {}

    public Pointage(int id, Date date, Time heureEntree, Time heureSortie, Employe employe, Paie paie) {
        this.id = id;
        this.date = date;
        this.heureEntree = heureEntree;
        this.heureSortie = heureSortie;
        this.employe = employe;
        this.paie = paie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Paie getPaie() {
        return paie;
    }

    public void setPaie(Paie paie) {
        this.paie = paie;
    }

    @Override
    public String toString() {
        return date +
                "                                           " + heureEntree +
                "                                " + heureSortie +
                "                                   " + employe.getUsername();
    }
}
