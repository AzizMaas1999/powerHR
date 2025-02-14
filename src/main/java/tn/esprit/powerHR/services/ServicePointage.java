package tn.esprit.powerHR.services;

import tn.esprit.powerHR.interfaces.IService;
import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.models.Pointage;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePointage implements IService<Pointage> {
    private Connection cnx ;

    public ServicePointage(){
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Pointage pointage) {
        //create Qry SQL
        //execute Qry
        String qry ="INSERT INTO `pointage`(`date`, `heureEntree`, `heureSortie`, `employe_id`, `paie_id`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setDate(1, pointage.getDate());
            pstm.setTime(2, pointage.getHeureEntree());
            pstm.setTime(3, pointage.getHeureSortie());
            pstm.setInt(4, pointage.getEmploye().getId());
            pstm.setInt(5, pointage.getPaie().getId());

            pstm.executeUpdate();
            System.out.println("Pointage added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public List<Pointage> getAll() {
        List<Pointage> pointages = new ArrayList<>();
        String qry ="SELECT * FROM `pointage`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()){
                Pointage p = new Pointage();
                p.setId(rs.getInt("id"));
                p.setDate(rs.getDate("date"));
                p.setHeureEntree(rs.getTime("heureEntree"));
                p.setHeureSortie(rs.getTime("heureSortie"));
                Employe employe = new Employe(rs.getInt("employe_id"));
                p.setEmploye(employe);
                Paie paie = new Paie(rs.getInt("paie_id"),0,0,null,null);
                p.setPaie(paie);

                pointages.add(p);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return pointages;
    }

    @Override
    public void update(Pointage pointage) {
        try {
            String req = "UPDATE `pointage` SET `date`= ?, `heureEntree`= ?, `heureSortie`= ? WHERE `id`= ?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setDate(1, pointage.getDate());
            ps.setTime(2, pointage.getHeureEntree());
            ps.setTime(3, pointage.getHeureSortie());
            ps.setInt(4, pointage.getId());

            ps.executeUpdate();
            System.out.println("Mise à jour de pointage réussie !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour : " + ex.getMessage());
        }
    }

    @Override
    public void delete(Pointage pointage) {
        try {
            String req = "DELETE FROM pointage WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, pointage.getId());

            ps.executeUpdate();
            System.out.println("pointage supprimée !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression : " + ex.getMessage());
        }
    }

}