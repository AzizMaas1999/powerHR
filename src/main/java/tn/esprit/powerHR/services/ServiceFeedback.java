package tn.esprit.powerHR.services;

import tn.esprit.powerHR.models.Feedback;
import tn.esprit.powerHR.interfaces.IService;
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
        String query = "INSERT INTO Feedback (idClFr, dateCreation, type, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, feedback.getIdClFr());
            preparedStatement.setString(2, feedback.getDateCreation());
            preparedStatement.setString(3, feedback.getType());
            preparedStatement.setString(4, feedback.getDescription());
            preparedStatement.executeUpdate();
            System.out.println("Feedback ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du feedback : " + e.getMessage());
        }
    }

    @Override
    public void update(Feedback feedback) {
        String query = "UPDATE Feedback SET idClFr = ?, dateCreation = ?, type = ?, description = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, feedback.getIdClFr());
            preparedStatement.setString(2, feedback.getDateCreation());
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
                feedback.setIdClFr(resultSet.getInt("idClFr"));
                feedback.setDateCreation(resultSet.getString("dateCreation"));
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
