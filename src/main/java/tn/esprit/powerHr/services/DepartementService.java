package tn.esprit.powerHr.services;

import tn.esprit.powerHr.models.Departement;
import tn.esprit.powerHr.interfaces.IDepartement;
import tn.esprit.powerHr.utils.MyDataBase;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Optional;

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

    // Stream operations
    public List<Departement> getDepartementsByEntreprise(int entrepriseId) {
        return getAll().stream()
            .filter(dep -> dep.getEntrepriseId() == entrepriseId)
            .collect(Collectors.toList());
    }

    public List<Departement> searchDepartements(String searchTerm) {
        return getAll().stream()
            .filter(dep -> 
                dep.getNom().toLowerCase().contains(searchTerm.toLowerCase()) ||
                dep.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
            .collect(Collectors.toList());
    }

    public List<String> getAllDepartementNames() {
        return getAll().stream()
            .map(Departement::getNom)
            .collect(Collectors.toList());
    }

    public List<Departement> getAllSortedByName() {
        return getAll().stream()
            .sorted(Comparator.comparing(Departement::getNom))
            .collect(Collectors.toList());
    }

    public Optional<Departement> findFirstByName(String name) {
        return getAll().stream()
            .filter(dep -> dep.getNom().equalsIgnoreCase(name))
            .findFirst();
    }

    public long countDepartementsInEntreprise(int entrepriseId) {
        return getAll().stream()
            .filter(dep -> dep.getEntrepriseId() == entrepriseId)
            .count();
    }

    public Map<Integer, List<Departement>> groupDepartementsByEntreprise() {
        return getAll().stream()
            .collect(Collectors.groupingBy(Departement::getEntrepriseId));
    }

    public Map<Integer, Long> getDepartementCountByEntreprise() {
        return getAll().stream()
            .collect(Collectors.groupingBy(
                Departement::getEntrepriseId,
                Collectors.counting()
            ));
    }

    public List<Departement> getTopNDepartements(int n) {
        return getAll().stream()
            .sorted(Comparator.comparing(Departement::getNom))
            .limit(n)
            .collect(Collectors.toList());
    }

    public List<Departement> getDepartementsWithDescription() {
        return getAll().stream()
            .filter(dep -> dep.getDescription() != null && !dep.getDescription().isEmpty())
            .collect(Collectors.toList());
    }

    public Map<String, List<Departement>> groupDepartementsByFirstLetter() {
        return getAll().stream()
            .collect(Collectors.groupingBy(
                dep -> dep.getNom().substring(0, 1).toUpperCase()
            ));
    }

    public DoubleSummaryStatistics getDepartementStatistics() {
        return getAll().stream()
            .mapToDouble(d -> d.getId())
            .summaryStatistics();
    }

    public boolean hasAnyDepartementWithName(String name) {
        return getAll().stream()
            .anyMatch(dep -> dep.getNom().equalsIgnoreCase(name));
    }

    public List<Departement> getDepartementsWithLongDescriptions(int minLength) {
        return getAll().stream()
            .filter(dep -> dep.getDescription() != null && 
                         dep.getDescription().length() >= minLength)
            .collect(Collectors.toList());
    }
} 