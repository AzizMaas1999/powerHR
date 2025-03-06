package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.Questionnaire;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceQuestionnaire implements IService<Questionnaire> {
    private Connection cnx;

    public ServiceQuestionnaire() {
        cnx = MyDataBase.getInstance().getCnx();
    }


    @Override
    public void add(Questionnaire questionnaire) {
        String qry = "INSERT INTO questionnaire (dateCreation, objet, description,employe_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setDate(1,questionnaire.getDateCreation());
            pstm.setString(2, questionnaire.getObjet());
            pstm.setString(3, questionnaire.getDescription());
            pstm.setInt(4, questionnaire.getEmploye().getId());


            pstm.executeUpdate();
            System.out.println("Questionnaire ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @Override
    public List<Questionnaire> getAll() {
        List<Questionnaire> questionnaireList = new ArrayList<>();
        String qry = "SELECT * FROM questionnaire";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            ServiceEmploye se = new ServiceEmploye();

            while (rs.next()) {
                Questionnaire questionnaire = new Questionnaire();
                questionnaire.setId(rs.getInt("id"));
                questionnaire.setDateCreation(rs.getDate("dateCreation"));
                questionnaire.setObjet(rs.getString("objet"));
                questionnaire.setDescription(rs.getString("description"));

                Employe employe = se.getById(rs.getInt("employe_id"));
                questionnaire.setEmploye(employe);
                questionnaireList.add(questionnaire);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }

        return questionnaireList;
    }

    @Override
    public void update(Questionnaire questionnaire) {
        String req = "UPDATE questionnaire SET dateCreation = ?, objet = ?, description = ? WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setDate(1, new java.sql.Date(questionnaire.getDateCreation().getTime()));
            ps.setString(2, questionnaire.getObjet());
            ps.setString(3, questionnaire.getDescription());
            ps.setInt(4, questionnaire.getId());

            ps.executeUpdate();
            System.out.println("Questionnaire mis à jour avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour : " + ex.getMessage());
        }
    }

    @Override
    public void delete(Questionnaire questionnaire) {
        String req = "DELETE FROM questionnaire WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, questionnaire.getId());

            ps.executeUpdate();
            System.out.println("Questionnaire supprimé avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression : " + ex.getMessage());
        }
    }
}
