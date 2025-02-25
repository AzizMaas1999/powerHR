package tn.esprit.powerHR.services;

import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.utils.MyDataBase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEmploye implements IService<Employe> {
    private Connection connection;

    public ServiceEmploye() {
        connection = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void add(Employe employe) {
        String queryGetDeptId = "SELECT id FROM departement WHERE nom = ?";
        String queryInsertEmploye = "INSERT INTO employe (username, password, poste, salaire, rib, codeSociale, departement_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmtDept = connection.prepareStatement(queryGetDeptId)) {
            stmtDept.setString(1, employe.getDepartement());
            ResultSet rs = stmtDept.executeQuery();

            if (rs.next()) {
                int departementId = rs.getInt("id");

                try (PreparedStatement stmtEmploye = connection.prepareStatement(queryInsertEmploye)) {
                    stmtEmploye.setString(1, employe.getUsername());
                    stmtEmploye.setString(2, employe.getPassword());
                    stmtEmploye.setString(3, String.valueOf(employe.getPoste()));
                    stmtEmploye.setDouble(4, employe.getSalaire());
                    stmtEmploye.setString(5, employe.getRib());
                    stmtEmploye.setString(6, employe.getCodeSociale());
                    stmtEmploye.setInt(7, departementId);

                    stmtEmploye.executeUpdate();
                    System.out.println("Employé ajouté avec succès !");
                }
            } else {
                System.err.println("Erreur : Département non trouvé !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'employé : " + e.getMessage());
        }
    }



    @Override
    public void update(Employe employe) {
        String query = "UPDATE employe SET username = ?, password= ?, poste = ?, salaire = ?, rib = ?, codeSociale = ?  WHERE id = ?";
        try(PreparedStatement preparedStatement=connection.prepareStatement(query)){

            preparedStatement.setString(1,employe.getUsername());
            preparedStatement.setString(2,employe.getPassword());
            preparedStatement.setString(3, String.valueOf(employe.getPoste()));
            preparedStatement.setDouble(4,employe.getSalaire());
            preparedStatement.setString(5,employe.getRib());
            preparedStatement.setString(6,employe.getCodeSociale());
            preparedStatement.executeUpdate();
            System.out.println("employe est modifier avec succès!");

        }catch (SQLException e) {
            System.err.println("Erreur lors de modification du l'employe:"+ e.getMessage());
        }
    }

    @Override
    public void delete(Employe employe) {
        String query = "DELETE * FROM employe WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, employe.getId());
            preparedStatement.executeUpdate();
            System.out.println("employe supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du employe : " + e.getMessage());
        }

    }

    @Override
    public List<Employe> getAll() {
        List<Employe> employes = new ArrayList<>();
        String query = "SELECT e.*, d.nom AS nom_departement FROM employe e " +
                "LEFT JOIN departement d ON e.departement_id = d.id";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Employe employe = new Employe();
                employe.setId(resultSet.getInt("id"));
                employe.setUsername(resultSet.getString("username"));
                employe.setPassword(resultSet.getString("password"));
                employe.setPoste(Poste.valueOf(resultSet.getString("poste")));
                employe.setSalaire(resultSet.getDouble("salaire"));
                employe.setRib(resultSet.getString("rib"));
                employe.setCodeSociale(resultSet.getString("codeSociale"));
                employe.setDepartement(resultSet.getString("nom_departement"));

                employes.add(employe);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des employés : " + e.getMessage());
        }
        return employes;
    }
    public List<String> getAllDepartements() {
        List<String> departements = new ArrayList<>();
        String query = "SELECT nom FROM departement"; // Query to fetch department names

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                departements.add(resultSet.getString("nom")); // Add each department name to the list
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des départements : " + e.getMessage());
        }

        return departements;
    }

}
