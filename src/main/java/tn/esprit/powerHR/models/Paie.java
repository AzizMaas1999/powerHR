package tn.esprit.powerHR.models;

import java.util.ArrayList;
import java.util.List;

public class Paie {
    private int id;
    private int nbjour;
    private float montant;
    private String mois;
    private List<Pointage> pointages = new ArrayList<>();

    public Paie() {}

    public Paie(int id, int nbjour, float montant, String mois, List<Pointage> pointages) {
        this.id = id;
        this.nbjour = nbjour;
        this.montant = montant;
        this.mois = mois;
        this.pointages = pointages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbjour() {
        return nbjour;
    }

    public void setNbjour(int nbjour) {
        this.nbjour = nbjour;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public List<Pointage> getPointages() {
        return pointages;
    }

    public void setPointages(Pointage pointage) {
        this.pointages.add(pointage);
    }

    @Override
    public String toString() {
        return nbjour +
                " | " + montant +
                " | " + mois;
    }
}
