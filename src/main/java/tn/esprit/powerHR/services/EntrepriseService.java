package tn.esprit.powerHR.services;

import tn.esprit.powerHR.models.Entreprise;
import tn.esprit.powerHR.interfaces.IEntreprise;
import tn.esprit.powerHR.utils.MyDataBase;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Optional;
import java.util.Map;
import java.util.DoubleSummaryStatistics;

public class EntrepriseService implements IEntreprise {
    private final Connection connection;

    public EntrepriseService() {
        connection = MyDataBase.getInstance().getcnx();
    }

    @Override
    public void add(Entreprise entreprise) throws Exception {
        // Validate required fields
        if (entreprise.getNom() == null || entreprise.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Company name is required");
        }
        if (entreprise.getSecteur() == null || entreprise.getSecteur().trim().isEmpty()) {
            throw new IllegalArgumentException("Sector is required");
        }
        if (entreprise.getMatriculeFiscale() == null || entreprise.getMatriculeFiscale().trim().isEmpty()) {
            throw new IllegalArgumentException("Matricule fiscale is required");
        }

        // Email can be null or empty
        String email = entreprise.getEmail();
        if (email != null) {
            email = email.trim();
            if (email.isEmpty()) {
                email = null;
            }
        }
        
        String query = "INSERT INTO entreprise (nom, secteur, matricule_fiscale, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, entreprise.getNom().trim());
            pst.setString(2, entreprise.getSecteur().trim());
            pst.setString(3, entreprise.getMatriculeFiscale().trim());
            pst.setString(4, email);
            
            pst.executeUpdate();
        }
    }

    @Override
    public void update(Entreprise entreprise) {
        try {
            String query = "UPDATE entreprise SET nom=?, secteur=?, matricule_fiscale=?, email=? WHERE id=?";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, entreprise.getNom());
                pst.setString(2, entreprise.getSecteur());
                pst.setString(3, entreprise.getMatriculeFiscale());
                pst.setString(4, entreprise.getEmail());
                pst.setInt(5, entreprise.getId());
                pst.executeUpdate();
            }

            showAlert(Alert.AlertType.INFORMATION, "Success", 
                "Company " + entreprise.getNom() + " has been updated successfully.");
} catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", 
                "Failed to update company: " + e.getMessage());
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
        entreprise.setEmail(rs.getString("email"));
        return entreprise;
    }

    // Stream operations
    public List<Entreprise> getEntreprisesBySector(String sector) {
        return getAll().stream()
            .filter(ent -> ent.getSecteur().equalsIgnoreCase(sector))
            .collect(Collectors.toList());
    }

    public List<Entreprise> searchEntreprises(String searchTerm) {
        return getAll().stream()
            .filter(ent -> 
                ent.getNom().toLowerCase().contains(searchTerm.toLowerCase()) ||
                ent.getSecteur().toLowerCase().contains(searchTerm.toLowerCase()) ||
                ent.getMatriculeFiscale().toLowerCase().contains(searchTerm.toLowerCase()))
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

    public long countEntreprisesBySector(String sector) {
        return getAll().stream()
            .filter(ent -> ent.getSecteur().equalsIgnoreCase(sector))
            .count();
    }

    public Map<String, Long> getEntrepriseCountBySector() {
        return getAll().stream()
            .collect(Collectors.groupingBy(
                Entreprise::getSecteur,
                Collectors.counting()
            ));
    }

    public List<Entreprise> getTopNEntreprises(int n) {
        return getAll().stream()
            .sorted(Comparator.comparing(Entreprise::getNom))
            .limit(n)
            .collect(Collectors.toList());
    }

    public DoubleSummaryStatistics getEntrepriseStatistics() {
        return getAll().stream()
            .mapToDouble(e -> e.getId())
            .summaryStatistics();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 