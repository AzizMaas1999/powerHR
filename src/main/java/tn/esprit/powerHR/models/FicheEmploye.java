package tn.esprit.powerHR.models;

import java.sql.Blob;

public class FicheEmploye{
        private int id;
        private String cin;
        private String nom;
        private String prenom;
        private String email;
        private String adresse;
        private String city;
        private String zip;
        private String numTel;
        private Blob pdfFile;

        public FicheEmploye() {
        }

        public FicheEmploye(int id, String cin, String nom, String prenom, String email, String adresse, String city, String zip, String numTel, Blob pdfFile) {
                this.id = id;
                setCin(cin);
                this.nom = nom;
                this.prenom = prenom;
                setEmail(email);
                this.adresse = adresse;
                this.city = city;
                this.zip = zip;
                setNumTel(numTel);
                this.pdfFile = pdfFile;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getCin() { return cin; }
        public void setCin(String cin) {
                if (!cin.matches("\\d{8}")) {
                        throw new IllegalArgumentException("CIN must be exactly 8 digits.");
                }
                this.cin = cin; }

        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }

        public String getPrenom() { return prenom; }
        public void setPrenom(String prenom) { this.prenom = prenom; }

        public String getEmail() { return email; }
        public void setEmail(String email) {
                if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                        throw new IllegalArgumentException("Invalid email format.");
                }
                this.email = email; }

        public String getAdresse() { return adresse; }
        public void setAdresse(String adresse) { this.adresse = adresse; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getZip() { return zip; }
        public void setZip(String zip) { this.zip = zip; }

        public String getNumTel() { return numTel; }
        public void setNumTel(String numTel) {
                if (!numTel.matches("^\\+?[0-9]{8,15}$")) {
                        throw new IllegalArgumentException("Phone number must be 8 to 15 digits, optionally starting with '+'.");
                }
                this.numTel = numTel; }

        public Blob getPdfFile() {
                return pdfFile;
        }

        public void setPdfFile(Blob pdfFile) {
                this.pdfFile = pdfFile;
        }

        @Override
        public String toString() {
                return "FicheEmploye{" +
                        "id=" + id +
                        ", cin='" + cin + '\'' +
                        ", nom='" + nom + '\'' +
                        ", prenom='" + prenom + '\'' +
                        ", email='" + email + '\'' +
                        ", adresse='" + adresse + '\'' +
                        ", city='" + city + '\'' +
                        ", zip='" + zip + '\'' +
                        ", numTel='" + numTel + '\'' +
                        ", pdfFile=" + pdfFile +
                        '}';
        }
}

