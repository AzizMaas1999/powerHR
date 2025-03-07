package tn.esprit.powerHR.services.ArtFactPaiement;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.ArtFactPaiement.Article;
import tn.esprit.powerHR.models.ArtFactPaiement.Facture;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceArticle implements IService<Article> {
    private final Connection cnx;

    public ServiceArticle() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Article article) {
        String qry = "INSERT INTO article (description, quantity, prixUni, TVA, facture_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setString(1, article.getDescription());
            pstm.setInt(2, article.getQuantity());
            pstm.setDouble(3, article.getPrixUni());
            pstm.setDouble(4, article.getTVA());
            pstm.setInt(5, article.getFacture().getId());


            pstm.executeUpdate();
            System.out.println("Article ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'article : " + e.getMessage());
        }
    }

    @Override
    public List<Article> getAll() {
        List<Article> articles = new ArrayList<>();
        String qry = "SELECT * FROM article";

        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(qry)) {

            while (rs.next()) {
                Facture facture = new Facture(rs.getInt("facture_id"), null, null, null, 0, null, null, null);
                Article article = new Article(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getDouble("prixUni"),
                        rs.getDouble("TVA"),
                        facture
                );
                articles.add(article);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des articles : " + e.getMessage());
        }
        return articles;
    }

    @Override
    public void update(Article article) {
        String qry = "UPDATE article SET description = ?, quantity = ?, prixUni = ?, TVA = ? WHERE id = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setString(1, article.getDescription());
            pstm.setInt(2, article.getQuantity());
            pstm.setDouble(3, article.getPrixUni());
            pstm.setDouble(4, article.getTVA());
            pstm.setInt(5, article.getId());

            pstm.executeUpdate();
            System.out.println(" Article mis à jour avec succès !");
        } catch (SQLException e) {
            System.err.println(" Erreur lors de la mise à jour de l'article : " + e.getMessage());
        }
    }

    @Override
    public void delete(Article article) {
        String qry = "DELETE FROM article WHERE id = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, article.getId());
            pstm.executeUpdate();
            System.out.println("Article supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'article : " + e.getMessage());
        }
    }

    // Méthode pour récupérer tous les articles (utile pour ComboBox)
    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        String req = "SELECT id, description, quantity, prixUni, TVA FROM article";

        try (PreparedStatement ps = cnx.prepareStatement(req);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                articles.add(new Article(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getDouble("prixUni"),
                        rs.getDouble("TVA"),
                        null // Pas besoin de facture ici
                ));
            }
        } catch (SQLException e) {
            System.err.println(" Erreur SQL: " + e.getMessage());
        }
        return articles;
    }
}
