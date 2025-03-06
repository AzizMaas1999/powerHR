package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.Questionnaire;
import tn.esprit.powerHR.models.RepQuestionnaire;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRepQuestionnaire implements IService<RepQuestionnaire> {
    private Connection cnx;

    public ServiceRepQuestionnaire() {
        cnx = MyDataBase.getInstance().getCnx();
    }


    @Override
    public void add(RepQuestionnaire rep) {
        String qry = "INSERT INTO repquestionnaire (dateCreation, reponse, questionnaire_id, employe_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setDate(1, rep.getDateCreation());
            pstm.setString(2, rep.getReponse());
            pstm.setInt(3, rep.getQuestionnaire().getId());
            pstm.setInt(4, rep.getEmploye().getId());

            pstm.executeUpdate();
            System.out.println("Réponse ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @Override
    public List<RepQuestionnaire> getAll() {
        List<RepQuestionnaire> repList = new ArrayList<>();
        String qry = "SELECT * FROM repquestionnaire";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            ServiceEmploye se = new ServiceEmploye();

            while (rs.next()) {
                RepQuestionnaire rep = new RepQuestionnaire();
                rep.setId(rs.getInt("id"));
                rep.setDateCreation(rs.getDate("dateCreation"));
                rep.setReponse(rs.getString("reponse"));

                Employe employe = se.getById(rs.getInt("employe_id"));
                rep.setEmploye(employe);

                Questionnaire questionnaire = new Questionnaire(rs.getInt("questionnaire_id"), null, null, null, null);
                rep.setQuestionnaire(questionnaire);

                repList.add(rep);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }

        return repList;
    }

    @Override
    public void update(RepQuestionnaire rep) {
        String req = "UPDATE repquestionnaire SET dateCreation = ?, reponse = ? WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setDate(1, new java.sql.Date(rep.getDateCreation().getTime()));
            ps.setString(2, rep.getReponse());
            ps.setInt(3, rep.getEmploye().getId());

            ps.executeUpdate();
            System.out.println("Réponse mise à jour avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour : " + ex.getMessage());
        }
    }

    @Override
    public void delete(RepQuestionnaire rep) {
        String req = "DELETE FROM repquestionnaire WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, rep.getId());

            ps.executeUpdate();
            System.out.println("Réponse supprimée avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression : " + ex.getMessage());
        }
    }
}
