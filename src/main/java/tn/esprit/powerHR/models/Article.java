package tn.esprit.powerHR.models;

public class Article  {
    private int id;
    private String description;
    private int quantity;
    private double prixUni;
    private double TVA;
    private Facture facture;

    public Article() {}

    public Article(int id, String description, int quantity, double prixUni, double TVA, Facture facture) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.prixUni = prixUni;
        this.TVA = TVA;
        this.facture = facture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrixUni() {
        return prixUni;
    }

    public void setPrixUni(double prixUni) {
        this.prixUni = prixUni;
    }

    public double getTVA() {
        return TVA;
    }

    public void setTVA(double TVA) {
        this.TVA = TVA;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", prixUni=" + prixUni +
                ", TVA=" + TVA +
                ", facture=" + facture +
                '}';
    }
}