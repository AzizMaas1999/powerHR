package tn.esprit.powerHR.models;

import tn.esprit.powerHR.models.ClfrFeedback.CLFr;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Facture {
    private int id;
    private String typeFact;
    private Date date;
    private String num;
    private double total;
    private CLFr clFr;
    private Paiement paiement;
    private List<Article> articles = new ArrayList<>();

    public Facture() {}

    public Facture(int id, String typeFact, Date date, String num, double total, Paiement paiement, CLFr clFr, List<Article> articles) {
        this.id = id;
        this.typeFact = typeFact;
        this.date = date;
        this.num = num;
        this.total = total;
        this.paiement = paiement;
        this.clFr = clFr;
        this.articles = articles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeFact() {
        return typeFact;
    }

    public void setTypeFact(String typeFact) {
        this.typeFact = typeFact;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public CLFr getClFr() {
        return clFr;
    }

    public void setClFr(CLFr clFr) {
        this.clFr = clFr;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "id=" + id +
                ", typeFact='" + typeFact + '\'' +
                ", date=" + date +
                ", num='" + num + '\'' +
                ", total=" + total +
                ", clFr=" + clFr +
                ", paiement=" + paiement +
                ", articles=" + articles +
                '}';
    }
}