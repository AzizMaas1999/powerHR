package tn.esprit.powerHR.models;

public class Employe {
    private int id;
    private String username;
    private String password;
    private String poste;
    private Double salaire;
    private String rib;
    private String codeSociale;

    public Employe() {
    }
    public Employe(int id,String username,String password, String poste, Double salaire, String rib, String codeSociale) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.poste = poste;
        this.salaire = salaire;
        this.rib = rib;
        this.codeSociale = codeSociale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getCodeSociale() {
        return codeSociale;
    }

    public void setCodeSociale(String codeSociale) {
        this.codeSociale = codeSociale;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", poste='" + poste + '\'' +
                ", salaire='" + salaire + '\'' +
                ", rib=" + rib +
                ", codeSociale='" + codeSociale + '\'' +
                '}';
    }
}
