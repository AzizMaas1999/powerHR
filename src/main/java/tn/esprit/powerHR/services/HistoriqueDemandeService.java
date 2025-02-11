package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.HistoriqueDemande;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoriqueDemandeService implements IService<HistoriqueDemande> {

    @Override
    public void add(HistoriqueDemande d) {
        try {
            String req = "INSERT INTO historiquedemande (id_directeur, id_demande, causeRefus, status) VALUES ("
                    + d.getIdDirecteur() + ", "
                    + d.getIdDemande() + ", '"
                    + d.getCauseRefus() + "', '"
                    + d.getStatus() + "')";

            Statement st = MyDataBase.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("Historique de demande ajouté avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout : " + ex.getMessage());
        }
    }

    @Override
    public void update(HistoriqueDemande d) {
        try {
            String req = "UPDATE historiquedemande SET id_directeur = ?, id_demande = ?, causeRefus = ?, status = ? WHERE id = ?";
            PreparedStatement ps = MyDataBase.getInstance().getCnx().prepareStatement(req);

            ps.setInt(1, d.getIdDirecteur());
            ps.setInt(2, d.getIdDemande());
            ps.setString(3, d.getCauseRefus());
            ps.setString(4, d.getStatus());
            ps.setInt(5, d.getId());

            ps.executeUpdate();
            System.out.println("Mise à jour de l'historique de demande réussie !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour : " + ex.getMessage());
        }
    }

    @Override
    public void delete(HistoriqueDemande d) {
        try {
            String req = "DELETE FROM historiquedemande WHERE id = ?";
            PreparedStatement ps = MyDataBase.getInstance().getCnx().prepareStatement(req);
            ps.setInt(1, d.getId());

            ps.executeUpdate();
            System.out.println("Historique de demande supprimé !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression : " + ex.getMessage());
        }
    }

    @Override
    public List<HistoriqueDemande> getAll() {
        List<HistoriqueDemande> liste = new ArrayList<>();
        try {
            String req = "SELECT * FROM historiquedemande";
            Statement st = MyDataBase.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                HistoriqueDemande d = new HistoriqueDemande(
                        rs.getInt("id"),
                        rs.getInt("id_directeur"),
                        rs.getInt("id_demande"),
                        rs.getString("causeRefus"),
                        rs.getString("status")
                );
                liste.add(d);
            }
            System.out.println("Récupération de l'historique des demandes réussie !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération : " + ex.getMessage());
        }
        return liste;
    }
}