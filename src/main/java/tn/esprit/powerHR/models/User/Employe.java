package tn.esprit.powerHR.models.User;

import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.ClfrFeedback.CLFr;
import tn.esprit.powerHR.models.DemRepQuest.Demande;
import tn.esprit.powerHR.models.DemRepQuest.RepQuestionnaire;


import java.util.ArrayList;
import java.util.List;

public class Employe {
    private int id;
    private String username;
    private String password;
    private Poste poste;
    private Double salaire;
    private String rib;
    private String codeSociale;
    private String nomDepartement;
    private List<Demande> demandes = new ArrayList<>();
    private List<RepQuestionnaire> RepQuestionnaires = new ArrayList<>();
    private List<Pointage> pointages = new  ArrayList<>();
    private List<CLFr> clFrs = new ArrayList<>();


    public Employe() {
    }

    public Employe(int id, String username, String password, Poste poste, Double salaire, String rib, String codeSociale, String nomDepartement, List<Demande> demandes, List<RepQuestionnaire> repQuestionnaires, List<Pointage> pointages, List<CLFr> clFrs) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.poste = poste;
        this.salaire = salaire;
        this.rib = rib;
        this.codeSociale = codeSociale;
        this.nomDepartement = nomDepartement;
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

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
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

    public String getDepartement() {
        return nomDepartement;
    }

    public void setDepartement(String nomDepartement) {
        this.nomDepartement = nomDepartement;
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
                ", departement=" + nomDepartement +
                ", demandes=" + demandes +
                ", RepQuestionnaires=" + RepQuestionnaires +
                ", pointages=" + pointages +
                ", clFrs=" + clFrs +
                '}';
    }
}
