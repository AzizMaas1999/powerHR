package tn.esprit.powerHR.test;

import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.models.Pointage;
import tn.esprit.powerHR.services.ServicePaie;
import tn.esprit.powerHR.services.ServicePointage;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.Date;
import java.sql.Time;

public class Main {
    public static void main(String[] args) {

        ServicePointage srvPnt = new ServicePointage();
//        Date date = Date.valueOf("2025-02-01");
//        Time hEnt = Time.valueOf("07:58:00");
//        Time hSor = Time.valueOf("17:10:00");
//        Pointage p = new Pointage(0,1,date,hEnt,hSor);
//        srvPnt.add(p);

//        Date date = Date.valueOf("2025-05-22");
//        Time hEnt = Time.valueOf("20:58:00");
//        Time hSor = Time.valueOf("05:00:00");
//        Pointage p = new Pointage(3,1,date,hEnt,hSor);
//        srvPnt.update(p);
//        srvPnt.delete(p);
        System.out.println(srvPnt.getAll());

        ServicePaie srvPaie = new ServicePaie();
//        Paie p = new Paie(3,3,26,2500,"Aout");
//        srvPaie.add(p);

//        Paie p = new Paie(2,0,24,220,"Juillet");
//        srvPaie.update(p);
//        srvPaie.delete(p);
        System.out.println(srvPaie.getAll());



    }
}