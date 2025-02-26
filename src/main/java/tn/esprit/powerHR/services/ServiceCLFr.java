package tn.esprit.powerHR.services;

import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.models.Employe;
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
        String query = "INSERT INTO CLFr (nom, matricule_fiscale, adresse, numtel, type, employe_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, clfr.getNom());
            preparedStatement.setString(2, clfr.getMatriculeFiscale());
            preparedStatement.setString(3, clfr.getAdresse());
            preparedStatement.setString(4, clfr.getNumTel());
            preparedStatement.setString(5, clfr.getType());
            preparedStatement.setInt(6, clfr.getEmploye().getId());
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
            preparedStatement.setString(2, clfr.getMatriculeFiscale());
            preparedStatement.setString(3, clfr.getAdresse());
            preparedStatement.setString(4, clfr.getNumTel());
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
                clfr.setMatriculeFiscale(resultSet.getString("matricule_fiscale"));
                clfr.setAdresse(resultSet.getString("adresse"));
                clfr.setNumTel(resultSet.getString("numtel"));
                clfr.setType(resultSet.getString("type"));
                Employe e = new Employe(resultSet.getInt("employe_id"),"fdkbgkndfg","fdkbgkndfg", Poste.Charges,445.2,"123456789125","fdkbgkndfg",null,null,null,null,null);
                clfr.setEmploye(e);
                clfrs.add(clfr);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des CLFrs : " + e.getMessage());
        }
        return clfrs;
    }
}
