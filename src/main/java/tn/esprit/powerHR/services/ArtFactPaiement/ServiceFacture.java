package tn.esprit.powerHR.services.ArtFactPaiement;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.CLFr;
import tn.esprit.powerHR.models.ArtFactPaiement.Facture;
import tn.esprit.powerHR.utils.MyDataBase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFacture implements IService<Facture> {
    private Connection cnx;

    public ServiceFacture() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Facture facture) {
        String qry = "INSERT INTO facture (typeFact, date, num, total, clfr_id, paiement_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setString(1, facture.getTypeFact());
            pstm.setDate(2, facture.getDate());
            pstm.setString(3, facture.getNum());
            pstm.setDouble(4, facture.getTotal());
            pstm.setInt(5, facture.getClFr().getId());
            pstm.setInt(6, facture.getPaiement().getId());
            pstm.executeUpdate();
            System.out.println("Facture ajoutée avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la facture : " + e.getMessage());
        }
    }

    @Override
    public List<Facture> getAll() {
        List<Facture> factures = new ArrayList<>();
        String qry = "SELECT * FROM facture";
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(qry)) {
            while (rs.next()) {
                Facture facture = new Facture();
                facture.setId(rs.getInt("id"));
                facture.setTypeFact(rs.getString("typeFact"));
                facture.setDate(rs.getDate("date"));
                facture.setNum(rs.getString("num"));
                facture.setTotal(rs.getDouble("total"));
                CLFr c = new CLFr(rs.getInt("clfr_id"),null,null,null,null,null,null,null,null);
                facture.setClFr(c);
                factures.add(facture);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des factures : " + e.getMessage());
        }
        return factures;
    }

    @Override
    public void update(Facture facture) {
        String qry = "UPDATE facture SET typeFact = ?, date = ?, num = ?, total = ? WHERE id = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setString(1, facture.getTypeFact());
            pstm.setDate(2, new java.sql.Date(facture.getDate().getTime()));
            pstm.setString(3, facture.getNum());
            pstm.setDouble(4, facture.getTotal());
            pstm.setInt(5, facture.getId());
            pstm.executeUpdate();
            System.out.println("Facture mise à jour avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la facture : " + e.getMessage());
        }
    }

    @Override
    public void delete(Facture facture) {
        String qry = "DELETE FROM facture WHERE id = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, facture.getId());
            pstm.executeUpdate();
            System.out.println("Facture supprimée avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la facture : " + e.getMessage());
        }
    }
}