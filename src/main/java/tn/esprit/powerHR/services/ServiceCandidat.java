package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.Candidat;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.EntrepriseDep.*;
import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCandidat implements IService<Candidat> {
    private Connection connection;

    public ServiceCandidat() {
        connection = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Candidat candidat) {
        String query = "INSERT INTO candidat (nom, prenom, email, telephone, cvPdf, entreprise_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, candidat.getNom());
            preparedStatement.setString(2, candidat.getPrenom());
            preparedStatement.setString(3, candidat.getEmail());
            preparedStatement.setString(4, candidat.getTelephone());
            preparedStatement.setBlob(5, candidat.getCvPdf());
            preparedStatement.setInt(6,candidat.getEntreprise().getId());
            preparedStatement.executeUpdate();
            System.out.println("Candidat ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du candidat : " + e.getMessage());
        }
    }

    @Override
    public void update(Candidat candidat) {
        String query = "UPDATE candidat SET nom = ?, prenom = ?, email = ?, telephone = ?, cvPdf = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, candidat.getNom());
            preparedStatement.setString(2, candidat.getPrenom());
            preparedStatement.setString(3, candidat.getEmail());
            preparedStatement.setString(4, candidat.getTelephone());
            preparedStatement.setBlob(5, candidat.getCvPdf());
            preparedStatement.setInt(6, candidat.getId());
            preparedStatement.executeUpdate();
            System.out.println("Candidat modifié avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du candidat : " + e.getMessage());
        }
    }

    @Override
    public void delete(Candidat candidat) {
        String query = "DELETE * FROM candidat WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, candidat.getId());
            preparedStatement.executeUpdate();
            System.out.println("Candidat supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du candidat : " + e.getMessage());
        }
    }

    @Override
    public List<Candidat> getAll() {
        List<Candidat> candidats = new ArrayList<>();
        String query = "SELECT * FROM candidat";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Candidat candidat = new Candidat();
                candidat.setId(resultSet.getInt("id"));
                candidat.setNom(resultSet.getString("nom"));
                candidat.setPrenom(resultSet.getString("prenom"));
                candidat.setEmail(resultSet.getString("email"));
                candidat.setTelephone(resultSet.getString("telephone"));
                Blob cvBlob = resultSet.getBlob("cvPdf");
                candidat.setCvPdf(cvBlob);
                Entreprise entreprise = new Entreprise((resultSet.getInt("entreprise_id")),null,null,null,null,true);
                candidat.setEntreprise(entreprise);

                candidats.add(candidat);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des candidats : " + e.getMessage());
        }
        return candidats;
    }
}
