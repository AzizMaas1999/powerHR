package tn.esprit.powerHR.services;

import tn.esprit.powerHR.models.Departement;
import tn.esprit.powerHR.interfaces.IDepartement;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartementService implements IDepartement {
    private Connection connection;

    public DepartementService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(Departement departement) {
        String sql = "INSERT INTO departement (nom, description) VALUES (?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, departement.getNom());//set fi var
            pst.setString(2, departement.getDescription());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Departement departement) {
        String sql = "UPDATE departement SET nom=?, description=? WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, departement.getNom());
            pst.setString(2, departement.getDescription());
            pst.setInt(3, departement.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM departement WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Departement getById(int id) {
        String sql = "SELECT * FROM departement WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Departement departement = new Departement();
                departement.setId(rs.getInt("id"));
                departement.setNom(rs.getString("nom"));
                departement.setDescription(rs.getString("description"));
                return departement;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Departement> getAll() {
        List<Departement> departements = new ArrayList<>();
        String sql = "SELECT * FROM departement";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Departement departement = new Departement();
                departement.setId(rs.getInt("id"));
                departement.setNom(rs.getString("nom"));
                departement.setDescription(rs.getString("description"));
                departements.add(departement);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return departements;
    }
} 