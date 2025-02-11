package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.DemandeConge;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DemandeCongeService implements IService<DemandeConge> {

    @Override
    public void add(DemandeConge d) {
        try {
            String req = "INSERT INTO demande (id_employe, type, cause, dateDebut, dateFin) VALUES ("
                    + d.getIdEmploye() + ", '"
                    + d.getType() + "', '"
                    + d.getCause() + "', '"
                    + d.getDateDebut() + "', '"
                    + d.getDateFin() + "')";

            Statement st = MyDataBase.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("Demande de congé ajoutée avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout : " + ex.getMessage());
        }
    }


    @Override
    public void update(DemandeConge d) {
        try {
            String req = "UPDATE demande SET id_Employe = ?, type = ?, cause = ?, dateDebut = ?, dateFin = ? WHERE id = ?";
            PreparedStatement ps = MyDataBase.getInstance().getCnx().prepareStatement(req);

            ps.setInt(1, d.getIdEmploye());
            ps.setString(2, d.getType());
            ps.setString(3, d.getCause());
            ps.setDate(4, d.getDateDebut());
            ps.setDate(5, d.getDateFin());
            ps.setInt(6, d.getId());

            ps.executeUpdate();
            System.out.println("Mise à jour de la demande de congé réussie !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour : " + ex.getMessage());
        }
    }

    @Override
    public void delete(DemandeConge d) {
        try {
            String req = "DELETE FROM demande WHERE id = ?";
            PreparedStatement ps = MyDataBase.getInstance().getCnx().prepareStatement(req);
            ps.setInt(1, d.getId());

            ps.executeUpdate();
            System.out.println("Demande de congé supprimée !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression : " + ex.getMessage());
        }
    }

    @Override
    public List<DemandeConge> getAll() {
        List<DemandeConge> liste = new ArrayList<>();
        try {

            String req = "SELECT * FROM demande WHERE type = 'Conges'";
            Statement st = MyDataBase.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                DemandeConge d = new DemandeConge(
                        rs.getInt("id"),
                        rs.getInt("id_Employe"),
                        rs.getString("type"),
                        rs.getString("cause"),
                        rs.getDate("dateDebut"),
                        rs.getDate("dateFin")
                );
                liste.add(d);
            }
            System.out.println("Récupération des demandes de congé (type 'Conges') réussie !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération : " + ex.getMessage());
        }
        return liste;
    }
}
