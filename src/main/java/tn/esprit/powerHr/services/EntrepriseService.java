package tn.esprit.powerHr.services;

import tn.esprit.powerHr.entities.Entreprise;
import tn.esprit.powerHr.interfaces.IEntreprise;
import tn.esprit.powerHr.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Optional;
import java.util.Map;

public class EntrepriseService implements IEntreprise {
    private Connection connection;

    public EntrepriseService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(Entreprise entreprise) {
        String query = "INSERT INTO entreprise (nom, secteur, matricule_fiscale) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, entreprise.getNom());
            pst.setString(2, entreprise.getSecteur());
            pst.setString(3, entreprise.getMatriculeFiscale());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Entreprise entreprise) {
        String query = "UPDATE entreprise SET nom=?, secteur=?, matricule_fiscale=? WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, entreprise.getNom());
            pst.setString(2, entreprise.getSecteur());
            pst.setString(3, entreprise.getMatriculeFiscale());
            pst.setInt(4, entreprise.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM entreprise WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Entreprise getById(int id) {
        String query = "SELECT * FROM entreprise WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return extractEntrepriseFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Entreprise> getAll() {
        List<Entreprise> entreprises = new ArrayList<>();
        String query = "SELECT * FROM entreprise";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                entreprises.add(extractEntrepriseFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return entreprises;
    }

    private Entreprise extractEntrepriseFromResultSet(ResultSet rs) throws SQLException {
        Entreprise entreprise = new Entreprise();
        entreprise.setId(rs.getInt("id"));
        entreprise.setNom(rs.getString("nom"));
        entreprise.setSecteur(rs.getString("secteur"));
        entreprise.setMatriculeFiscale(rs.getString("matricule_fiscale"));
        return entreprise;
    }

    // Stream-based methods
    public List<Entreprise> getEntreprisesBySector(String sector) {
        return getAll().stream()
            .filter(ent -> ent.getSecteur().equalsIgnoreCase(sector))
            .collect(Collectors.toList());
    }

    public List<Entreprise> searchEntreprises(String searchTerm) {
        return getAll().stream()
            .filter(ent -> 
                ent.getNom().toLowerCase().contains(searchTerm.toLowerCase()) ||
                ent.getMatriculeFiscale().contains(searchTerm) ||
                ent.getSecteur().toLowerCase().contains(searchTerm.toLowerCase()))
            .collect(Collectors.toList());
    }

    public List<Entreprise> getAllSortedByName() {
        return getAll().stream()
            .sorted(Comparator.comparing(Entreprise::getNom))
            .collect(Collectors.toList());
    }

    public Map<String, List<Entreprise>> groupEntreprisesBySector() {
        return getAll().stream()
            .collect(Collectors.groupingBy(Entreprise::getSecteur));
    }

    public Optional<Entreprise> findByMatriculeFiscale(String matriculeFiscale) {
        return getAll().stream()
            .filter(ent -> ent.getMatriculeFiscale().equals(matriculeFiscale))
            .findFirst();
    }

    public List<String> getAllEntrepriseNames() {
        return getAll().stream()
            .map(Entreprise::getNom)
            .collect(Collectors.toList());
    }

    public List<String> getUniqueSectors() {
        return getAll().stream()
            .map(Entreprise::getSecteur)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    public boolean existsByMatriculeFiscale(String matriculeFiscale) {
        return getAll().stream()
            .anyMatch(ent -> ent.getMatriculeFiscale().equals(matriculeFiscale));
    }

    public long countEntreprisesInSector(String sector) {
        return getAll().stream()
            .filter(ent -> ent.getSecteur().equalsIgnoreCase(sector))
            .count();
    }
} 