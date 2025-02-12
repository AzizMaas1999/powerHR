package tn.esprit.powerHR.models;

import java.util.Date;

public class Facture {
    private int id;               // Correspond à la colonne `id` (int)
    private int idClfr;           // Correspond à la colonne `id_clfr` (int)
    private String typeFact;      // Correspond à la colonne `typeFact` (enum)
    private Date date;            // Correspond à la colonne `date` (date)
    private String num;           // Correspond à la colonne `num` (varchar)
    private double total;         // Correspond à la colonne `total` (decimal)

    // Constructeurs
    public Facture() {}

    public Facture(int id, int idClfr, String typeFact, Date date, String num, double total) {
        this.id = id;
        this.idClfr = idClfr;
        this.typeFact = typeFact;
        this.date = date;
        this.num = num;
        this.total = total;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdClfr() { return idClfr; }
    public void setIdClfr(int idClfr) { this.idClfr = idClfr; }

    public String getTypeFact() { return typeFact; }
    public void setTypeFact(String typeFact) {
        if (!typeFact.equals("Facture") && !typeFact.equals("Avoir")) {
            throw new IllegalArgumentException("Le type de facture doit être 'Facture' ou 'Avoir'.");
        }
        this.typeFact = typeFact;
    }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getNum() { return num; }
    public void setNum(String num) { this.num = num; }

    public double getTotal() { return total; }
    public void setTotal(double total) {
        if (total < 0) {
            throw new IllegalArgumentException("Le total ne peut pas être négatif.");
        }
        this.total = total;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "id=" + id +
                ", idClfr=" + idClfr +
                ", typeFact='" + typeFact + '\'' +
                ", date=" + date +
                ", num='" + num + '\'' +
                ", total=" + total +
                '}';
    }
}