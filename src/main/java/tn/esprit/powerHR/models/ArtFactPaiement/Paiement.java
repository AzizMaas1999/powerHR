package tn.esprit.powerHR.models.ArtFactPaiement;

import java.util.ArrayList;
import java.sql.*;
import java.util.List;

public class Paiement {
    private int id;
    private Date date;
    private String mode;
    private String reference;
    private double montant;
    private List<Facture> factures = new ArrayList<>();

    public Paiement() {}

    public Paiement(int id, Date date, String mode, String reference, List<Facture> factures, double montant) {
        this.id = id;
        this.date = date;
        this.mode = mode;
        this.reference = reference;
        this.factures = factures;
        this.montant = montant;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public List<Facture> getFactures() {
        return factures;
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
    }

    @Override
    public String toString() {
        return
                "    " + reference + '\'' +
                "    " + date +
                "    " + montant +
                "    " + mode + '\''

                ;
    }
}