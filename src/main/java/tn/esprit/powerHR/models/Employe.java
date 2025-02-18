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

    public Employe(int id, String username, String password, String poste, Double salaire, String rib, String codeSociale) {
        this.id = id;
        setUsername(username);
        setPassword(password);
        setPoste(poste);
        this.salaire = salaire;
        setRib(rib);
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
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("username cannot be null or empty");
        }
        this.username = username;
    }

    public String getPassword() {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        if (!poste.equals("DirecteurRH") && !poste.equals("chargesRH") && !poste.equals("ouvrier")) {
            throw new IllegalArgumentException("Poste must be 'DirecteurRH', 'chargesRH', or 'ouvrier'");
        }
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
        if (!rib.matches("\\d{12}")) {
            throw new IllegalArgumentException("RIB must be exactly 12 numero");
        }
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
                ", salaire=" + salaire +
                ", rib='" + rib + '\'' +
                ", codeSociale='" + codeSociale + '\'' +
                '}';
    }
}
