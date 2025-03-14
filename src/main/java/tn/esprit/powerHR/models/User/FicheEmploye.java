package tn.esprit.powerHR.models.User;

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
        private String cvPdfUrl;
        private Employe employe;

        public FicheEmploye() {
        }

        public FicheEmploye(int id, String cin, String nom, String prenom, String email, String adresse, String city, String zip, String numTel, String cvPdfUrl , Employe employe) {
                this.id = id;
                this.cin = cin;
                this.nom = nom;
                this.prenom = prenom;
                this.email = email;
                this.adresse = adresse;
                this.city = city;
                this.zip = zip;
                this.numTel = numTel;
                this.cvPdfUrl = cvPdfUrl;
                this.employe = employe;
        }

        public int getId() {return id;}

        public void setId(int id) {this.id = id;}

        public String getCin() {
                return cin;
        }

        public void setCin(String cin) {
                this.cin = cin;
        }

        public String getNom() {
                return nom;
        }

        public void setNom(String nom) {
                this.nom = nom;
        }

        public String getPrenom() {
                return prenom;
        }

        public void setPrenom(String prenom) {
                this.prenom = prenom;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getAdresse() {
                return adresse;
        }

        public void setAdresse(String adresse) {
                this.adresse = adresse;
        }

        public String getCity() {
                return city;
        }

        public void setCity(String city) {
                this.city = city;
        }

        public String getZip() {
                return zip;
        }

        public void setZip(String zip) {
                this.zip = zip;
        }

        public String getNumTel() {
                return numTel;
        }

        public void setNumTel(String numTel) {
                this.numTel = numTel;
        }
        public String getCvPdfUrl() {
                return cvPdfUrl;
        }

        public void setCvPdfUrl(String cvPdfUrl) {
                this.cvPdfUrl = cvPdfUrl;}


        public Employe getEmploye() {
                return employe;
        }

        public void setEmploye(Employe employe) {
                this.employe = employe;
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
                        ", cvPdfUrl=" + cvPdfUrl +
                        ", employe=" + employe +
                        '}';
        }
}

