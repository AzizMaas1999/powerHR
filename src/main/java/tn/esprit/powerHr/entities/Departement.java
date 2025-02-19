package tn.esprit.powerHr.entities;

public class Departement {
    private int id;
    private String nom;
    private String description;
    private int entrepriseId;

    // Constructor
    public Departement() {}

    public Departement(String nom, String description, int entrepriseId) {
        this.nom = nom;
        this.description = description;
        this.entrepriseId = entrepriseId;
    }

    public Departement(String name, String description) {
        this.nom = name;
        this.description = description;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getEntrepriseId() { return entrepriseId; }
    public void setEntrepriseId(int entrepriseId) { this.entrepriseId = entrepriseId; }

    @Override
    public String toString() {
        return "Departement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", entrepriseId=" + entrepriseId +
                '}';
    }
} 