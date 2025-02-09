package tn.esprit.powerHR.services;

import tn.esprit.powerHR.models.Entreprise;
import tn.esprit.powerHR.interfaces.IEntreprise;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrepriseService implements IEntreprise {
    private Connection connection;

    public EntrepriseService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(Entreprise entreprise) {
        String sql = "INSERT INTO entreprise (nom, type, secteur, matricule_fiscale) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, entreprise.getNom());
            pst.setString(2, entreprise.getType());
            pst.setString(3, entreprise.getSecteur());
            pst.setString(4, entreprise.getMatriculeFiscale());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Entreprise entreprise) {
        String sql = "UPDATE entreprise SET nom=?, type=?, secteur=?, matricule_fiscale=? WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, entreprise.getNom());
            pst.setString(2, entreprise.getType());
            pst.setString(3, entreprise.getSecteur());
            pst.setString(4, entreprise.getMatriculeFiscale());
            pst.setInt(5, entreprise.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM entreprise WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Entreprise getById(int id) {
        String sql = "SELECT * FROM entreprise WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Entreprise entreprise = new Entreprise();
                entreprise.setId(rs.getInt("id"));
                entreprise.setNom(rs.getString("nom"));
                entreprise.setType(rs.getString("type"));
                entreprise.setSecteur(rs.getString("secteur"));
                entreprise.setMatriculeFiscale(rs.getString("matricule_fiscale"));
                return entreprise;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Entreprise> getAll() {
        List<Entreprise> entreprises = new ArrayList<>();
        String sql = "SELECT * FROM entreprise";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Entreprise entreprise = new Entreprise();
                entreprise.setId(rs.getInt("id"));
                entreprise.setNom(rs.getString("nom"));
                entreprise.setType(rs.getString("type"));
                entreprise.setSecteur(rs.getString("secteur"));
                entreprise.setMatriculeFiscale(rs.getString("matricule_fiscale"));
                entreprises.add(entreprise);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return entreprises;
    }
} 