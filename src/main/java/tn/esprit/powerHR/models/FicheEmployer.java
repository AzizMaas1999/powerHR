package tn.esprit.powerHR.models;

public class FicheEmployer {
    private int id;
    private String cin;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String city;
    private String zip;
    private String numTel;
    private String versionPdf;

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }

    public String getNumTel() { return numTel; }
    public void setNumTel(String numTel) { this.numTel = numTel; }

    public String getVersionPdf() { return versionPdf; }
    public void setVersionPdf(String versionPdf) { this.versionPdf = versionPdf; }

    @Override
    public String toString() {
        return "FicheEmployer{" +
                "id=" + id +
                ", cin='" + cin + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", numTel='" + numTel + '\'' +
                ", versionPdf='" + versionPdf + '\'' +
                '}';
    }

}
