package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.Article;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceArticle implements IService<Article> {
    private Connection cnx;

    public ServiceArticle() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Article article) {
        // Requête SQL pour insérer un article
        String qry = "INSERT INTO article (id_facture, description, quantity, prixUni, TVA) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            // Définir les paramètres de la requête
            pstm.setInt(1, article.getIdFacture());
            pstm.setString(2, article.getDescription());
            pstm.setInt(3, article.getQuantity());
            pstm.setDouble(4, article.getPrixUni());
            pstm.setDouble(5, article.getTVA());

            // Exécuter la requête
            pstm.executeUpdate();
            System.out.println("Article ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'article : " + e.getMessage());
        }
    }

    @Override
    public List<Article> getAll() {
        List<Article> articles = new ArrayList<>();
        // Requête SQL pour récupérer tous les articles
        String qry = "SELECT * FROM article";
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(qry)) {
            // Parcourir les résultats de la requête
            while (rs.next()) {
                Article a = new Article();
                a.setId(rs.getInt("id"));
                a.setIdFacture(rs.getInt("id_facture"));
                a.setDescription(rs.getString("description"));
                a.setQuantity(rs.getInt("quantity"));
                a.setPrixUni(rs.getDouble("prixUni"));
                a.setTVA(rs.getDouble("TVA"));
                articles.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des articles : " + e.getMessage());
        }
        return articles;
    }

    @Override
    public void update(Article article) {
        // Requête SQL pour mettre à jour un article
        String qry = "UPDATE article SET id_facture = ?, description = ?, quantity = ?, prixUni = ?, TVA = ? WHERE id = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            // Définir les paramètres de la requête
            pstm.setInt(1, article.getIdFacture());
            pstm.setString(2, article.getDescription());
            pstm.setInt(3, article.getQuantity());
            pstm.setDouble(4, article.getPrixUni());
            pstm.setDouble(5, article.getTVA());
            pstm.setInt(6, article.getId());

            // Exécuter la requête
            pstm.executeUpdate();
            System.out.println("Article mis à jour avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'article : " + e.getMessage());
        }
    }

    @Override
    public void delete(Article article) {
        // Requête SQL pour supprimer un article
        String qry = "DELETE FROM article WHERE id = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            // Définir le paramètre de la requête
            pstm.setInt(1, article.getId());

            // Exécuter la requête
            pstm.executeUpdate();
            System.out.println("Article supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'article : " + e.getMessage());
        }
    }
}