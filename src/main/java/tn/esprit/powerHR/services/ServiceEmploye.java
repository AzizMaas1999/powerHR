package tn.esprit.powerHR.services;

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
        String query = "INSERT INTO employe (username, password, poste, salaire, rib,codeSociale) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, employe.getUsername());
            preparedStatement.setString(2, employe.getPassword());
            preparedStatement.setString(3, employe.getPoste());
            preparedStatement.setDouble(4, employe.getSalaire());
            preparedStatement.setString(5,employe.getRib());
            preparedStatement.setString(6, employe.getCodeSociale());
            preparedStatement.executeUpdate();
            System.out.println("employe ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du employe : " + e.getMessage()); }

    }

    @Override
    public void update(Employe employe) {
        String query = "UPDATE employe SET username = ?, password= ?, poste = ?, salaire = ?, rib = ?, codeSociale = ?  WHERE id = ?";
        try(PreparedStatement preparedStatement=connection.prepareStatement(query)){

            preparedStatement.setString(1,employe.getUsername());
            preparedStatement.setString(2,employe.getPassword());
            preparedStatement.setString(3,employe.getPoste());
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
        String query = "DELETE FROM employe WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, employe.getId());
            preparedStatement.executeUpdate();
            System.out.println("employe supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du employe : " + e.getMessage());
        }

    }

    @Override
    public List getAll() {
        List<Employe> employes = new ArrayList<>();
        String query = "SELECT * FROM employe";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Employe employe = new Employe();
                employe.setId(resultSet.getInt("id"));
                employe.setUsername(resultSet.getString("username"));
                employe.setPassword(resultSet.getString("password"));
                employe.setPoste(resultSet.getString("poste"));
                employe.setSalaire(resultSet.getDouble("Salaire"));
                employe.setRib(resultSet.getString("Rib"));
                employe.setCodeSociale(resultSet.getString("CodePostale"));
                employes.add(employe);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des employes : " + e.getMessage());
        }
        return employes;
    }
}
