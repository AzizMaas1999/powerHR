package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.Feedback;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFeedback implements IService<Feedback> {
    private Connection connection;

    public ServiceFeedback() {
        connection = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Feedback feedback) {
        // Vérifier si idClFr existe dans la table CLFr
        String checkQuery = "SELECT COUNT(*) FROM CLFr WHERE id = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            checkStatement.setInt(1, feedback.getIdClFr());
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Si l'idClFr existe, vous pouvez insérer le feedback
                String query = "INSERT INTO Feedback (id_clfr, dateCreation, type, description) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, feedback.getIdClFr());
                    preparedStatement.setDate(2, feedback.getDateCreation());  // Utilisation de setDate pour la date
                    preparedStatement.setString(3, feedback.getType());
                    preparedStatement.setString(4, feedback.getDescription());
                    preparedStatement.executeUpdate();
                    System.out.println("Feedback ajouté avec succès !");
                }
            } else {
                System.err.println("Erreur : idClFr n'existe pas dans la table CLFr.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du feedback : " + e.getMessage());
        }
    }


    @Override
    public void update(Feedback feedback) {
        String query = "UPDATE Feedback SET id_clfr = ?, dateCreation = ?, type = ?, description = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, feedback.getIdClFr());
            preparedStatement.setDate(2, feedback.getDateCreation()); // Utilisation de setDate pour correspondre à 'date'
            preparedStatement.setString(3, feedback.getType());
            preparedStatement.setString(4, feedback.getDescription());
            preparedStatement.setInt(5, feedback.getId());
            preparedStatement.executeUpdate();
            System.out.println("Feedback modifié avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du feedback : " + e.getMessage());
        }
    }

    @Override
    public void delete(Feedback feedback) {
        String query = "DELETE FROM Feedback WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, feedback.getId());
            preparedStatement.executeUpdate();
            System.out.println("Feedback supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du feedback : " + e.getMessage());
        }
    }

    @Override
    public List<Feedback> getAll() {
        List<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT * FROM Feedback";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(resultSet.getInt("id"));
                feedback.setIdClFr(resultSet.getInt("id_clfr"));
                feedback.setDateCreation(resultSet.getDate("dateCreation")); // Utilisation de getDate pour le type 'date'
                feedback.setType(resultSet.getString("type"));
                feedback.setDescription(resultSet.getString("description"));
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des feedbacks : " + e.getMessage());
        }
        return feedbacks;
    }
}
