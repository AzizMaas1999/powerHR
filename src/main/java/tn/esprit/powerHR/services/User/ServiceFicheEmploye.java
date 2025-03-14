package tn.esprit.powerHR.services.User;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.models.User.FicheEmploye;
import tn.esprit.powerHR.utils.MyDataBase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFicheEmploye implements IService<FicheEmploye> {
    private Connection connection;
    public ServiceFicheEmploye() {connection = MyDataBase.getInstance().getCnx();}

    @Override
    public void add(FicheEmploye ficheEmploye) {
        String getIdQuery = "SELECT id FROM employe WHERE username LIKE ?";
        int employeId = -1;
        try (PreparedStatement stmt = connection.prepareStatement(getIdQuery)) {
            stmt.setString(1, "%" + ficheEmploye.getNom() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    employeId = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println(" Erreur lors de la récupération de l'ID employé : " + e.getMessage());
            return;
        }

        if (employeId == -1) {
            System.err.println(" Aucun employé trouvé où le nom est inclus dans le username !");
            return;
        }

        String query = "INSERT INTO fiche_employe (cin, nom, prenom, email, adresse, city, zip, numTel, cvPdfUrl, employe_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ficheEmploye.getCin());
            preparedStatement.setString(2, ficheEmploye.getNom());
            preparedStatement.setString(3, ficheEmploye.getPrenom());
            preparedStatement.setString(4, ficheEmploye.getEmail());
            preparedStatement.setString(5, ficheEmploye.getAdresse());
            preparedStatement.setString(6, ficheEmploye.getCity());
            preparedStatement.setString(7, ficheEmploye.getZip());
            preparedStatement.setString(8, ficheEmploye.getNumTel());
            preparedStatement.setString(9, ficheEmploye.getCvPdfUrl());
            preparedStatement.setInt(10, employeId);
            preparedStatement.executeUpdate();
            System.out.println(" FicheEmploye ajoutée avec succès !");
        } catch (SQLException e) {
            System.err.println(" Erreur lors de l'ajout de la fiche employé : " + e.getMessage());
        }
    }

    @Override
    public void update(FicheEmploye ficheEmploye) {
        String getIdQuery = "SELECT id FROM employe WHERE username LIKE ?";
        int employeId = -1;
        try (PreparedStatement stmt = connection.prepareStatement(getIdQuery)) {
            stmt.setString(1, "%" + ficheEmploye.getNom() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    employeId = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println(" Erreur lors de la récupération de l'ID employé : " + e.getMessage());
            return;
        }

        if (employeId == -1) {
            System.err.println(" Aucun employé trouvé où le nom est inclus dans le username !");
            return;
        }

        String query = "UPDATE fiche_employe SET cin=?, nom=?, prenom=?, email=?, adresse=?, city=?, zip=?, numTel=?, cvPdfUrl=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ficheEmploye.getCin());
            preparedStatement.setString(2, ficheEmploye.getNom());
            preparedStatement.setString(3, ficheEmploye.getPrenom());
            preparedStatement.setString(4, ficheEmploye.getEmail());
            preparedStatement.setString(5, ficheEmploye.getAdresse());
            preparedStatement.setString(6, ficheEmploye.getCity());
            preparedStatement.setString(7, ficheEmploye.getZip());
            preparedStatement.setString(8, ficheEmploye.getNumTel());
            preparedStatement.setString(9, ficheEmploye.getCvPdfUrl());
            preparedStatement.setInt(10, employeId);
            preparedStatement.executeUpdate();
            System.out.println("FicheEmploye mise à jour avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de la fiche employé : " + e.getMessage());
        }
    }

    @Override
    public void delete(FicheEmploye ficheEmploye) {
        String query = "DELETE * FROM fiche_employe WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ficheEmploye.getId());
            preparedStatement.executeUpdate();
            System.out.println("FicheEmploye supprimée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la fiche employé : " + e.getMessage());
        }
    }

    @Override
    public List<FicheEmploye> getAll() {
        List<FicheEmploye> ficheEmployes = new ArrayList<>();
        String query = "SELECT * FROM fiche_employe";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                FicheEmploye ficheEmploye = new FicheEmploye();
                ficheEmploye.setId(resultSet.getInt("id"));
                ficheEmploye.setCin(resultSet.getString("cin"));
                ficheEmploye.setNom(resultSet.getString("nom"));
                ficheEmploye.setPrenom(resultSet.getString("prenom"));
                ficheEmploye.setEmail(resultSet.getString("email"));
                ficheEmploye.setAdresse(resultSet.getString("adresse"));
                ficheEmploye.setCity(resultSet.getString("city"));
                ficheEmploye.setZip(resultSet.getString("zip"));
                ficheEmploye.setNumTel(resultSet.getString("numTel"));
                ficheEmploye.setCvPdfUrl(resultSet.getString("cvPdfUrl"));
                Employe employe = new Employe(resultSet.getInt("employe_id"),"","",null,null,null,null,null,null,null,null,null);
                ficheEmploye.setEmploye(employe);

                ficheEmployes.add(ficheEmploye);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des fiches employé : " + e.getMessage());
        }
        return ficheEmployes;
    }
}
