package tn.esprit.powerHR.services;

import tn.esprit.powerHR.models.FicheEmployer;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFicheEmployer {
    private Connection connection;

    public ServiceFicheEmployer() {
        connection = MyDataBase.getInstance().getCnx();
    }
    public void add(FicheEmployer fiche) {
        String query = "INSERT INTO fiche_employe (cin, nom, prenom, email, adresse, city, zip, numtel, versionpdf) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, fiche.getCin());
            statement.setString(2, fiche.getNom());
            statement.setString(3, fiche.getPrenom());
            statement.setString(4, fiche.getEmail());
            statement.setString(5, fiche.getAdresse());
            statement.setString(6, fiche.getCity());
            statement.setString(7, fiche.getZip());
            statement.setString(8, fiche.getNumTel());
            statement.setString(9, fiche.getVersionPdf());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        fiche.setId(generatedKeys.getInt(1));  // Récupère l'ID généré et l'assigne à l'objet
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<FicheEmployer> getAll() {
        List<FicheEmployer> fiches = new ArrayList<>();
        String query = "SELECT * FROM fiche_employe";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                FicheEmployer fiche = new FicheEmployer();
                fiche.setId(resultSet.getInt("id"));
                fiche.setCin(resultSet.getString("cin"));
                fiche.setNom(resultSet.getString("nom"));
                fiche.setPrenom(resultSet.getString("prenom"));
                fiche.setEmail(resultSet.getString("email"));
                fiche.setAdresse(resultSet.getString("adresse"));
                fiche.setCity(resultSet.getString("city"));
                fiche.setZip(resultSet.getString("zip"));
                fiche.setNumTel(resultSet.getString("numtel"));
                fiche.setVersionPdf(resultSet.getString("versionpdf"));
                fiches.add(fiche);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fiches;
    }
    public void update(FicheEmployer fiche) {
        String query = "UPDATE fiche_employe SET nom = ?, prenom = ?, email = ?, adresse = ?, city = ?, zip = ?, numtel = ?, versionpdf = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fiche.getNom());
            statement.setString(2, fiche.getPrenom());
            statement.setString(3, fiche.getEmail());
            statement.setString(4, fiche.getAdresse());
            statement.setString(5, fiche.getCity());
            statement.setString(6, fiche.getZip());
            statement.setString(7, fiche.getNumTel());
            statement.setString(8, fiche.getVersionPdf());
            statement.setInt(9, fiche.getId()); // Id de l'employé à modifier
            statement.executeUpdate();
            System.out.println("FicheEmployeur mise à jour avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(FicheEmployer fiche) {
        if (fiche.getId() == 0) {
            System.out.println("L'ID de la fiche employé est invalide, suppression impossible.");
            return;
        }

        String query = "DELETE FROM fiche_employe WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, fiche.getId()); // Id de l'employé à supprimer
            statement.executeUpdate();
            System.out.println("FicheEmployeur supprimée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
