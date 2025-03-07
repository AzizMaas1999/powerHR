package tn.esprit.powerHR.services.User;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.EntrepriseDep.Entreprise;
import tn.esprit.powerHR.models.User.Candidat;
import tn.esprit.powerHR.utils.MyDataBase;
import tn.esprit.powerHR.utils.User.MinIOUtils;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCandidat implements IService<Candidat> {
    private Connection connection;

    public ServiceCandidat() {
        connection = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void add(Candidat candidat) {
        String query = "INSERT INTO candidat (nom, prenom, email, telephone, cvPdfUrl, entreprise_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // ✅ Use cvPdfUrl from Candidat object (already uploaded)
            String cvPdfUrl = candidat.getCvPdfUrl();

            // 🔴 Debugging: Check if cvPdfUrl is correctly assigned
            System.out.println("cvPdfUrl before saving to DB: " + cvPdfUrl);

            if (cvPdfUrl != null && !cvPdfUrl.isEmpty()) {
                preparedStatement.setString(1, candidat.getNom());
                preparedStatement.setString(2, candidat.getPrenom());
                preparedStatement.setString(3, candidat.getEmail());
                preparedStatement.setString(4, candidat.getTelephone());
                preparedStatement.setString(5, cvPdfUrl); // ✅ Use URL, not file path
                preparedStatement.setInt(6, candidat.getEntreprise().getId());

                preparedStatement.executeUpdate();
                System.out.println("✅ Candidat ajouté avec succès !");
            } else {
                System.err.println("❌ cvPdfUrl is NULL or EMPTY! Check the upload process.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du candidat : " + e.getMessage());
        }
    }


    @Override
    public void update(Candidat candidat) {
        String query = "UPDATE candidat SET nom = ?, prenom = ?, email = ?, telephone = ?, cvPdfUrl = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Get file path from Candidat object
            String filePath = candidat.getFilePath(); // Ensure filePath is set in the Candidat object
            if (filePath != null && !filePath.isEmpty()) {
                System.out.println("Uploading CV with path: " + filePath);  // Debugging line to check file path

                String cvPdfUrl = MinIOUtils.uploadCV("cv-bucket", filePath, filePath.substring(filePath.lastIndexOf(File.separator) + 1));
                if (cvPdfUrl != null) {
                    preparedStatement.setString(1, candidat.getNom());
                    preparedStatement.setString(2, candidat.getPrenom());
                    preparedStatement.setString(3, candidat.getEmail());
                    preparedStatement.setString(4, candidat.getTelephone());
                    preparedStatement.setString(5, cvPdfUrl);
                    preparedStatement.setInt(6, candidat.getId());
                    preparedStatement.executeUpdate();
                    System.out.println("Candidat modifié avec succès !");
                } else {
                    System.err.println("Erreur lors de l'upload du CV");
                }
            } else {
                System.err.println("Le chemin du fichier CV est vide ou invalide.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du candidat : " + e.getMessage());
        }
    }



    @Override
    public void delete(Candidat candidat) {
        String query = "DELETE FROM candidat WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, candidat.getId());
            preparedStatement.executeUpdate();
            System.out.println("Candidat supprimé avec succès !");

            String cvPdfUrl = candidat.getCvPdfUrl();
            if (cvPdfUrl != null && !cvPdfUrl.isEmpty()) {

                String fileName = cvPdfUrl.substring(cvPdfUrl.lastIndexOf("/") + 1);
                MinIOUtils.deleteCV("cvs", fileName);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du candidat : " + e.getMessage());
        }
    }

    @Override
    public List<Candidat> getAll() {
        List<Candidat> candidats = new ArrayList<>();
        String query = "SELECT * FROM candidat";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Candidat candidat = new Candidat();
                candidat.setId(resultSet.getInt("id"));
                candidat.setNom(resultSet.getString("nom"));
                candidat.setPrenom(resultSet.getString("prenom"));
                candidat.setEmail(resultSet.getString("email"));
                candidat.setTelephone(resultSet.getString("telephone"));
                candidat.setCvPdfUrl(resultSet.getString("cvPdfUrl"));
                Entreprise entreprise = new Entreprise(resultSet.getInt("entreprise_id"), "", null, null, null, false);
                candidat.setEntreprise(entreprise);

                candidats.add(candidat);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des candidats : " + e.getMessage());
        }
        return candidats;
    }
}
