package tn.esprit.powerHR.models;

public class Paie {
    private int id,id_pointage;
    private int nbjour;
    private float montant;
    private String mois;

    public Paie() {}

    public Paie(int id, int id_pointage, int nbjour, float montant, String mois) {
        this.id = id;
        this.id_pointage = id_pointage;
        this.nbjour = nbjour;
        this.montant = montant;
        this.mois = mois;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pointage() {
        return id_pointage;
    }

    public void setId_pointage(int id_pointage) {
        this.id_pointage = id_pointage;
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

    @Override
    public String toString() {
        return "Paie{" +
                "id=" + id +
                ", id_pointage=" + id_pointage +
                ", nbjour=" + nbjour +
                ", montant=" + montant +
                ", mois='" + mois + '\'' +
                '}';
    }
}
