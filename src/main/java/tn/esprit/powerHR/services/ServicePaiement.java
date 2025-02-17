package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.Paiement;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ServicePaiement implements IService<Paiement> {
    private Connection cnx;

    public ServicePaiement() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Paiement paiement) {
        String qry = "INSERT INTO paiement (date, mode, reference, montant) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setDate(1, paiement.getDate());
            pstm.setString(2, paiement.getMode());
            pstm.setString(3, paiement.getReference());
            pstm.setDouble(4, paiement.getMontant());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du paiement : " + e.getMessage());
        }
    }

    @Override
    public List<Paiement> getAll() {
        List<Paiement> paiements = new ArrayList<>();
        String qry = "SELECT * FROM paiement";
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(qry)) {
            while (rs.next()) {
                Paiement p = new Paiement();
                p.setId(rs.getInt("id"));
                p.setDate(rs.getDate("date"));
                p.setMode(rs.getString("mode"));
                p.setReference(rs.getString("reference"));
                p.setMontant(rs.getDouble("montant"));
                paiements.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des paiements : " + e.getMessage());
        }
        return paiements;
    }

    @Override
    public void update(Paiement paiement) {
        String qry = "UPDATE paiement SET id_facture = ?, date = ?, mode = ?, reference = ?, montant = ? WHERE id = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setDate(1, new java.sql.Date(paiement.getDate().getTime()));
            pstm.setString(2, paiement.getMode());
            pstm.setString(3, paiement.getReference());
            pstm.setDouble(4, paiement.getMontant());
            pstm.setInt(5, paiement.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du paiement : " + e.getMessage());
        }
    }

    @Override
    public void delete(Paiement paiement) {
        String qry = "DELETE FROM paiement WHERE id = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, paiement.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du paiement : " + e.getMessage());
        }
    }
}