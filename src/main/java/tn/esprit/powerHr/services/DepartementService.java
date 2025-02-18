package tn.esprit.powerHr.services;

import tn.esprit.powerHr.entities.Departement;
import tn.esprit.powerHr.interfaces.IDepartement;
import tn.esprit.powerHr.utils.MyDataBase;

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
        String query = "INSERT INTO departement (nom, description, entreprise_id) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, departement.getNom());
            pst.setString(2, departement.getDescription());
            pst.setInt(3, departement.getEntrepriseId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Departement departement) {
        String query = "UPDATE departement SET nom=?, description=?, entreprise_id=? WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, departement.getNom());
            pst.setString(2, departement.getDescription());
            pst.setInt(3, departement.getEntrepriseId());
            pst.setInt(4, departement.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM departement WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
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
                return extractDepartementFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Departement> getAll() {
        List<Departement> departements = new ArrayList<>();
        String query = "SELECT * FROM departement";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                departements.add(extractDepartementFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return departements;
    }

    private Departement extractDepartementFromResultSet(ResultSet rs) throws SQLException {
        Departement departement = new Departement();
        departement.setId(rs.getInt("id"));
        departement.setNom(rs.getString("nom"));
        departement.setDescription(rs.getString("description"));
        departement.setEntrepriseId(rs.getInt("entreprise_id"));
        return departement;
    }
} 