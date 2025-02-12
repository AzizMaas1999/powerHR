package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.models.Pointage;
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
        String qry ="INSERT INTO `Paie`(`id_pointage`, `nbJour`, `montant`, `mois`) VALUES (?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, paie.getId_pointage());
            pstm.setInt(2, paie.getNbjour());
            pstm.setFloat(3, paie.getMontant());
            pstm.setString(4, paie.getMois());

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
                p.setId_pointage(rs.getInt("id_pointage"));
                p.setNbjour(rs.getInt("nbjour"));
                p.setMontant(rs.getFloat("montant"));
                p.setMois(rs.getString("mois"));

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
            String req = "UPDATE `paie` SET `nbJour`= ?, `montant`= ?, `mois`= ? WHERE `id`= ?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, paie.getNbjour());
            ps.setFloat(2, paie.getMontant());
            ps.setString(3, paie.getMois());
            ps.setInt(4, paie.getId());

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
}