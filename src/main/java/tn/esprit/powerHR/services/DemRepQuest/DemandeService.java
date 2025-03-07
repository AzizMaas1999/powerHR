package tn.esprit.powerHR.services.DemRepQuest;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.DemRepQuest.Demande;
import tn.esprit.powerHR.services.User.ServiceEmploye;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DemandeService implements IService<Demande> {

    private Connection cnx;

    public DemandeService() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Demande d) {
        String req = "INSERT INTO demande (dateCreation, type, dateDebut, dateFin, salaire, cause, status, employe_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setDate(1, d.getDateCreation());
            pstm.setString(2, d.getType());
            pstm.setDate(3, d.getDateDebut());
            pstm.setDate(4, d.getDateFin());
            pstm.setDouble(5, d.getSalaire());
            pstm.setString(6, d.getCause());
            pstm.setString(7, d.getStatus());
            pstm.setInt(8, d.getEmploye().getId());

            pstm.executeUpdate();
            System.out.println("Demande de congé ajoutée avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout : " + ex.getMessage());
        }
    }



    @Override
    public void update(Demande d) {
        try {
            String req = "UPDATE demande SET dateCreation = ?, type = ?, dateDebut = ?, dateFin = ?, salaire = ?, cause = ?, status = ? WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setDate(1, d.getDateCreation());
            ps.setString(2, d.getType());
            ps.setDate(3, d.getDateDebut());
            ps.setDate(4, d.getDateFin());
            ps.setFloat(5, d.getSalaire());
            ps.setString(6, d.getCause());
            ps.setString(7, d.getStatus());
            ps.setInt(8, d.getId());

            ps.executeUpdate();
            System.out.println("Mise à jour de la demande de congé réussie !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour : " + ex.getMessage());
        }
    }

    @Override
    public void delete(Demande d) {
        try {
            String req = "DELETE FROM demande WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, d.getId());

            ps.executeUpdate();
            System.out.println("Demande de congé supprimée !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression : " + ex.getMessage());
        }
    }

    @Override
    public List<Demande> getAll() {
        List<Demande> liste = new ArrayList<>();
        try {

            String req = "SELECT * FROM demande";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            ServiceEmploye se = new ServiceEmploye();

            while (rs.next()) {
                Demande d = new Demande();
                d.setId(rs.getInt("id"));
                d.setDateCreation(rs.getDate("dateCreation"));
                d.setType(rs.getString("type"));
                d.setDateDebut(rs.getDate("dateDebut"));
                d.setDateFin(rs.getDate("dateFin"));
                d.setSalaire(rs.getFloat("salaire"));
                d.setCause(rs.getString("cause"));
                d.setStatus(rs.getString("status"));
//                Employe e = new Employe(rs.getInt("employe_id"),null,null,null,null,null,null,null,null,null,null,null);
                d.setEmploye(se.getById(rs.getInt("employe_id")));
                liste.add(d);
            }
            System.out.println("Récupération des demandes  réussie !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération : " + ex.getMessage());
        }
        return liste;
    }
}
