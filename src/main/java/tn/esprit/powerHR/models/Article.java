package tn.esprit.powerHR.models;

public class Article {
    private int id;               // Correspond à la colonne `id` (int)
    private int idFacture;        // Correspond à la colonne `id_facture` (int)
    private String description;   // Correspond à la colonne `description` (varchar)
    private int quantity;         // Correspond à la colonne `quantity` (int)
    private double prixUni;       // Correspond à la colonne `prixUni` (float)
    private double TVA;           // Correspond à la colonne `TVA` (float)

    // Constructeur par défaut
    public Article() {}

    // Constructeur complet
    public Article(int id, int idFacture, String description, int quantity, double prixUni, double TVA) {
        this.id = id;
        this.idFacture = idFacture;
        this.description = description;
        this.quantity = quantity;
        this.prixUni = prixUni;
        this.TVA = TVA;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdFacture() { return idFacture; }
    public void setIdFacture(int idFacture) { this.idFacture = idFacture; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("La quantité ne peut pas être négative.");
        this.quantity = quantity;
    }

    public double getPrixUni() { return prixUni; }
    public void setPrixUni(double prixUni) {
        if (prixUni < 0) throw new IllegalArgumentException("Le prix unitaire ne peut pas être négatif.");
        this.prixUni = prixUni;
    }

    public double getTVA() { return TVA; }
    public void setTVA(double TVA) {
        if (TVA < 0) throw new IllegalArgumentException("La TVA ne peut pas être négative.");
        this.TVA = TVA;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", idFacture=" + idFacture +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", prixUni=" + prixUni +
                ", TVA=" + TVA +
                '}';
    }
}