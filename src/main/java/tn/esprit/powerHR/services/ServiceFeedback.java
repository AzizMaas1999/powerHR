package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.models.Feedback;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFeedback implements IService<Feedback> {
    private Connection cnx;

    public ServiceFeedback() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Feedback feedback) {
        String qry = "INSERT INTO feedback (dateCreation, type, description, clfr_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setDate(1, feedback.getDateCreation());
            pstm.setString(2, feedback.getType());
            pstm.setString(3, feedback.getDescription());
            pstm.setInt(4, feedback.getClfr().getId());
            pstm.executeUpdate();
            System.out.println("feedback ajoutée avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la feedback : " + e.getMessage());
        }
    }

    @Override
    public List<Feedback> getAll() {
        List<Feedback> feedbacks = new ArrayList<>();
        String qry = "SELECT * FROM feedback";
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(qry)) {
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setDateCreation(rs.getDate("dateCreation"));
                feedback.setType(rs.getString("type"));
                feedback.setDescription(rs.getString("description"));
                CLFr c = new CLFr(rs.getInt("clfr_id"),null,null,null,null,null,null,null,null);
                feedback.setClfr(c);
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des feedbacks : " + e.getMessage());
        }
        return feedbacks;
    }

    @Override
    public void update(Feedback feedback) {
        String qry = "UPDATE feedback SET dateCreation = ?, type = ?, description = ? WHERE id = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setDate(1, feedback.getDateCreation());
            pstm.setString(2, feedback.getType());
            pstm.setString(3, feedback.getDescription());
            pstm.setInt(4, feedback.getId());
            pstm.executeUpdate();
            System.out.println("feedback mise à jour avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la feedback : " + e.getMessage());
        }
    }

    @Override
    public void delete(Feedback feedback) {
        String qry = "DELETE FROM feedback WHERE id = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, feedback.getId());
            pstm.executeUpdate();
            System.out.println("feedback supprimée avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la feedback : " + e.getMessage());
        }
    }
}
