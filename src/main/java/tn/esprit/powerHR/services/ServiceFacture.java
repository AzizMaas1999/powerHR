package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.Facture;
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
        String qry = "INSERT INTO facture (id_clfr, typeFact, date, num, total) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, facture.getIdClfr());
            pstm.setString(2, facture.getTypeFact());
            pstm.setDate(3, new java.sql.Date(facture.getDate().getTime()));
            pstm.setString(4, facture.getNum());
            pstm.setDouble(5, facture.getTotal());
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
                facture.setIdClfr(rs.getInt("id_clfr"));
                facture.setTypeFact(rs.getString("typeFact"));
                facture.setDate(rs.getDate("date"));
                facture.setNum(rs.getString("num"));
                facture.setTotal(rs.getDouble("total"));
                factures.add(facture);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des factures : " + e.getMessage());
        }
        return factures;
    }

    @Override
    public void update(Facture facture) {
        String qry = "UPDATE facture SET id_clfr = ?, typeFact = ?, date = ?, num = ?, total = ? WHERE id = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, facture.getIdClfr());
            pstm.setString(2, facture.getTypeFact());
            pstm.setDate(3, new java.sql.Date(facture.getDate().getTime()));
            pstm.setString(4, facture.getNum());
            pstm.setDouble(5, facture.getTotal());
            pstm.setInt(6, facture.getId());
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