package tn.esprit.powerHR.services.PaiePointage;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.PaiePointage.Paie;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePaie implements IService<Paie> {
    private Connection cnx ;

    public ServicePaie(){
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Paie paie) {
        //create Qry SQL
        //execute Qry
        String qry ="INSERT INTO `Paie`(`nbJour`, `montant`, `mois`, `annee`) VALUES (?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, paie.getNbjour());
            pstm.setFloat(2, paie.getMontant());
            pstm.setString(3, paie.getMois());
            pstm.setString(4, paie.getAnnee());

            pstm.executeUpdate();
            System.out.println("Paie added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public List<Paie> getAll() {
        List<Paie> paies = new ArrayList<>();
        String qry ="SELECT * FROM `paie`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()){
                Paie p = new Paie();
                p.setId(rs.getInt("id"));
                p.setNbjour(rs.getInt("nbjour"));
                p.setMontant(rs.getFloat("montant"));
                p.setMois(rs.getString("mois"));
                p.setAnnee(rs.getString("annee"));

                paies.add(p);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return paies;
    }

    @Override
    public void update(Paie paie) {
        try {
            String req = "UPDATE `paie` SET `nbJour`= ?, `montant`= ? WHERE `id`= ?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, paie.getNbjour());
            ps.setFloat(2, paie.getMontant());
            ps.setInt(3, paie.getId());

            ps.executeUpdate();
            System.out.println("Mise à jour de paie réussie !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour : " + ex.getMessage());
        }
    }

    @Override
    public void delete(Paie paie) {
        try {
            String req = "DELETE FROM paie WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, paie.getId());

            ps.executeUpdate();
            System.out.println("paie supprimée !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression : " + ex.getMessage());
        }
    }

    public Paie getById(int id) {
        String query = "SELECT * FROM paie WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Paie(
                        resultSet.getInt("id"),
                        resultSet.getInt("nbjour"),
                        resultSet.getFloat("montant"),
                        resultSet.getString("mois"),
                        resultSet.getString("annee"),
                        null
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la paie : " + e.getMessage());
        }
        return null;
    }
}