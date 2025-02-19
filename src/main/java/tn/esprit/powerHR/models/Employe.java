package tn.esprit.powerHR.models;

import java.util.ArrayList;
import java.util.List;

public class Employe {
    private int id;
    private String username;
    private String password;
    private String poste;
    private Double salaire;
    private String rib;
    private String codeSociale;
    private Departement departement;
    private List<Demande> demandes = new ArrayList<>();
    private List<RepQuestionnaire> RepQuestionnaires = new ArrayList<>();
    private List<Pointage> pointages = new  ArrayList<>();
    private List<CLFr> clFrs = new ArrayList<>();


    public Employe() {
    }

    public Employe(int id, String username, String password, String poste, Double salaire, String rib, String codeSociale, Departement departement, List<Demande> demandes, List<RepQuestionnaire> repQuestionnaires, List<Pointage> pointages, List<CLFr> clFrs) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.poste = poste;
        this.salaire = salaire;
        this.rib = rib;
        this.codeSociale = codeSociale;
        this.departement = departement;
        this.demandes = demandes;
        RepQuestionnaires = repQuestionnaires;
        this.pointages = pointages;
        this.clFrs = clFrs;
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

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }

    public List<RepQuestionnaire> getRepQuestionnaires() {
        return RepQuestionnaires;
    }

    public void setRepQuestionnaires(List<RepQuestionnaire> repQuestionnaires) {
        RepQuestionnaires = repQuestionnaires;
    }

    public List<Pointage> getPointages() {
        return pointages;
    }

    public void setPointages(List<Pointage> pointages) {
        this.pointages = pointages;
    }

    public List<CLFr> getClFrs() {
        return clFrs;
    }

    public void setClFrs(List<CLFr> clFrs) {
        this.clFrs = clFrs;
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
                ", departement=" + departement +
                ", demandes=" + demandes +
                ", RepQuestionnaires=" + RepQuestionnaires +
                ", pointages=" + pointages +
                ", clFrs=" + clFrs +
                '}';
    }
}
