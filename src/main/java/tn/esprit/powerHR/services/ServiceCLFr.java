package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCLFr implements IService<CLFr> {
    private Connection connection;

    public ServiceCLFr() {
        connection = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(CLFr clfr) {
        String query = "INSERT INTO CLFr (nom, matricule_fiscale, adresse, numtel, type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, clfr.getNom());
            preparedStatement.setInt(2, clfr.getMatriculeFiscale()); // int(11)
            preparedStatement.setString(3, clfr.getAdresse());
            preparedStatement.setInt(4, clfr.getNumtel()); // int(11)
            preparedStatement.setString(5, clfr.getType());
            preparedStatement.executeUpdate();
            System.out.println("CLFr ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du CLFr : " + e.getMessage());
        }
    }

    @Override
    public void update(CLFr clfr) {
        String query = "UPDATE CLFr SET nom = ?, matricule_fiscale = ?, adresse = ?, numtel = ?, type = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, clfr.getNom());
            preparedStatement.setInt(2, clfr.getMatriculeFiscale()); // int(11)
            preparedStatement.setString(3, clfr.getAdresse());
            preparedStatement.setInt(4, clfr.getNumtel()); // int(11)
            preparedStatement.setString(5, clfr.getType());
            preparedStatement.setInt(6, clfr.getId());
            preparedStatement.executeUpdate();
            System.out.println("CLFr modifié avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du CLFr : " + e.getMessage());
        }
    }

    @Override
    public void delete(CLFr clfr) {
        String query = "DELETE FROM CLFr WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clfr.getId());
            preparedStatement.executeUpdate();
            System.out.println("CLFr supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du CLFr : " + e.getMessage());
        }
    }

    @Override
    public List<CLFr> getAll() {
        List<CLFr> clfrs = new ArrayList<>();
        String query = "SELECT * FROM CLFr";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                CLFr clfr = new CLFr();
                clfr.setId(resultSet.getInt("id"));
                clfr.setNom(resultSet.getString("nom"));
                clfr.setMatriculeFiscale(resultSet.getInt("matricule_fiscale")); // int(11)
                clfr.setAdresse(resultSet.getString("adresse"));
                clfr.setNumtel(resultSet.getInt("numtel")); // int(11)
                clfr.setType(resultSet.getString("type"));
                clfrs.add(clfr);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des CLFrs : " + e.getMessage());
        }
        return clfrs;
    }
}
