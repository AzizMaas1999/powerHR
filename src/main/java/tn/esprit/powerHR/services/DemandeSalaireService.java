package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.DemandeSalaire;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DemandeSalaireService implements IService<DemandeSalaire> {

    @Override
    public void add(DemandeSalaire d) {
        try {
            String req = "INSERT INTO demande (id_employe, type, salaire) VALUES ("
                    + d.getIdEmploye() + ", '"
                    + d.getType() + "', "
                    + d.getSalaire() + ")";

            Statement st = MyDataBase.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("Demande de salaire ajoutée avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout : " + ex.getMessage());
        }
    }


    @Override
    public void update(DemandeSalaire d) {
        try {
            String req = "UPDATE demande SET idEmploye = ?, type = ?, salaire = ? WHERE id = ?";
            PreparedStatement ps = MyDataBase.getInstance().getCnx().prepareStatement(req);

            ps.setInt(1, d.getIdEmploye());
            ps.setString(2, d.getType());
            ps.setFloat(3, d.getSalaire());
            ps.setInt(4, d.getId());

            ps.executeUpdate();
            System.out.println("Mise à jour de la demande de salaire effectuée !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour : " + ex.getMessage());
        }
    }

    @Override
    public void delete(DemandeSalaire d) {
        try {
            String req = "DELETE FROM demande WHERE id = ?";
            PreparedStatement ps = MyDataBase.getInstance().getCnx().prepareStatement(req);
            ps.setInt(1, d.getId());

            ps.executeUpdate();
            System.out.println("Demande de salaire supprimée !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression : " + ex.getMessage());
        }
    }

    @Override
    public List<DemandeSalaire> getAll() {
        List<DemandeSalaire> liste = new ArrayList<>();
        try {

            String req = "SELECT * FROM demande WHERE type = 'Augmentation Salaire'";
            Statement st = MyDataBase.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                DemandeSalaire d = new DemandeSalaire(
                        rs.getInt("id"),
                        rs.getInt("id_Employe"),
                        rs.getString("type"),
                        rs.getFloat("salaire")
                );
                liste.add(d);
            }
            System.out.println("Récupération des demandes de salaire (type 'Augmentation Salaire') réussie ");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération : " + ex.getMessage());
        }
        return liste;
    }
}
