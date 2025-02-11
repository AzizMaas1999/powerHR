package tn.esprit.powerHR.models;


public class DemandeSalaire {
    private int id, idEmploye;
    private String type;
    private float salaire;

    public DemandeSalaire() {
    }

    public DemandeSalaire(int id, int idEmploye, String type, float salaire) {
        this.id = id;
        this.idEmploye = idEmploye;
        this.type = type;
        this.salaire = salaire;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    @Override
    public String toString() {
        return "DemandeSalaire{" +
                "id=" + id +
                ", idEmploye=" + idEmploye +
                ", type='" + type + '\'' +
                ", salaire=" + salaire +
                "}\n";
    }
}

