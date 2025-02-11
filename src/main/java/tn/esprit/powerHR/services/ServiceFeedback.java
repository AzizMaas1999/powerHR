package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.Feedback;
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
        String query = "INSERT INTO feedback (id, id_clfr, type, dateCreation, description) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, feedback.getId());
            preparedStatement.setInt(2, feedback.getId_clfr());
            preparedStatement.setString(3, feedback.getType());
            preparedStatement.setDate(4, feedback.getDateCreation());
            preparedStatement.setString(5,feedback.getDescription());
            preparedStatement.executeUpdate();
            System.out.println("feedback ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du feedback : " + e.getMessage()); }
    }

    @Override
    public void update(Feedback feedback) {
        String query = "UPDATE feedback SET id=?, id_clfr=?, type=?, dateCreation=?, description=?  WHERE id = ?";
        try(PreparedStatement preparedStatement=connection.prepareStatement(query)){

            preparedStatement.setInt(1,feedback.getId());
            preparedStatement.setInt(2,feedback.getId_clfr());
            preparedStatement.setString(3,feedback.getType());
            preparedStatement.setDate(4,feedback.getDateCreation());
            preparedStatement.setString(5,feedback.getDescription());
            preparedStatement.executeUpdate();
            System.out.println("feedback est modifier avec succès!");

        }catch (SQLException e) {
            System.err.println("Erreur lors de modification du l'feedback:"+ e.getMessage());
        }
    }

    @Override
    public void delete(Feedback feedback) {
        String query = "DELETE FROM feedback WHERE id = ?";
        try (PreparedStatement preparedStatement=connection.prepareStatement(query)){
            
            preparedStatement.setInt(1,feedback.getId());
            preparedStatement.executeUpdate();
            System.out.println("feedback supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du feedback : " + e.getMessage());
        }
    }

    @Override
    public List<Feedback> getAll() {
        List<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT * FROM feedback";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(resultSet.getInt("id"));
                feedback.setId_clfr(resultSet.getInt("Id_clfr"));
                feedback.setType(resultSet.getString("Type"));
                feedback.setDateCreation(resultSet.getDate("Date"));
                feedback.setDescription(resultSet.getString("Description"));
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des feedbacks : " + e.getMessage());
        }
        return feedbacks;
    }
}
