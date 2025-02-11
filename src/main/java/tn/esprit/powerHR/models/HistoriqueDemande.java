package tn.esprit.powerHR.models;

public class HistoriqueDemande {
    private int id;
    private int idDirecteur;
    private int idDemande;
    private String causeRefus;
    private String status;

    public HistoriqueDemande() {
    }

    public HistoriqueDemande(int id, int idDirecteur, int idDemande, String causeRefus, String status) {
        this.id = id;
        this.idDirecteur = idDirecteur;
        this.idDemande = idDemande;
        this.causeRefus = causeRefus;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDirecteur() {
        return idDirecteur;
    }

    public void setIdDirecteur(int idDirecteur) {
        this.idDirecteur = idDirecteur;
    }

    public int getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }

    public String getCauseRefus() {
        return causeRefus;
    }

    public void setCauseRefus(String causeRefus) {
        this.causeRefus = causeRefus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;

    }
    @Override
    public String toString() {
        return "HistoriqueDemande{" +
                "id=" + id +
                ", idDirecteur=" + idDirecteur +
                ", idDemande=" + idDemande +
                ", causeRefus='" + causeRefus + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}