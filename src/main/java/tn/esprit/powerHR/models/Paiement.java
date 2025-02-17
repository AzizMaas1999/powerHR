package tn.esprit.powerHR.models;

import java.util.Date;

public class Paiement {
    private int id;
    private int idFacture;
    private Date date;
    private String mode;
    private String reference;
    private double montant;

    // Constructeurs
    public Paiement() {}

    public Paiement(int id, int idFacture, Date date, String mode, String reference, double montant) {
        this.id = id;
        this.idFacture = idFacture;
        this.date = date;
        this.mode = mode;
        this.reference = reference;
        this.montant = montant;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdFacture() { return idFacture; }
    public void setIdFacture(int idFacture) { this.idFacture = idFacture; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", idFacture=" + idFacture +
                ", date=" + date +
                ", mode='" + mode + '\'' +
                ", reference='" + reference + '\'' +
                ", montant=" + montant +
                '}';
    }
}