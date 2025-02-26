package tn.esprit.powerHR.test;

import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.models.Pointage;
import tn.esprit.powerHR.services.ServicePaie;
import tn.esprit.powerHR.services.ServicePointage;
import tn.esprit.powerHR.utils.MyDataBase;

import java.sql.Date;
import java.sql.Time;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

//        ServicePointage srvPnt = new ServicePointage();
////        Date date = Date.valueOf("2025-02-01");
////        Time hEnt = Time.valueOf("07:58:00");
////        Time hSor = Time.valueOf("17:10:00");
////        Pointage p = new Pointage(0,1,date,hEnt,hSor);
////        srvPnt.add(p);
//
////        Date date = Date.valueOf("2025-05-22");
////        Time hEnt = Time.valueOf("20:58:00");
////        Time hSor = Time.valueOf("05:00:00");
////        Pointage p = new Pointage(3,1,date,hEnt,hSor);
////        srvPnt.update(p);
////        srvPnt.delete(p);
//        System.out.println(srvPnt.getAll());
//
//        ServicePaie srvPaie = new ServicePaie();
////        Paie p = new Paie(3,3,26,2500,"Aout");
////        srvPaie.add(p);
//
////        Paie p = new Paie(2,0,24,220,"Juillet");
////        srvPaie.update(p);
////        srvPaie.delete(p);
//        System.out.println(srvPaie.getAll());
        List<Pointage> pointageList = new ArrayList<>();
        pointageList.add(new Pointage(11, Date.valueOf("2025-02-01"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
        pointageList.add(new Pointage(21, Date.valueOf("2025-02-01"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
        pointageList.add(new Pointage(31, Date.valueOf("2025-02-01"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
        pointageList.add(new Pointage(11, Date.valueOf("2025-02-02"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
        pointageList.add(new Pointage(21, Date.valueOf("2025-02-02"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
        pointageList.add(new Pointage(31, Date.valueOf("2025-02-02"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
        pointageList.add(new Pointage(11, Date.valueOf("2025-02-03"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
        pointageList.add(new Pointage(21, Date.valueOf("2025-02-03"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
        pointageList.add(new Pointage(31, Date.valueOf("2025-02-03"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
        List<Integer> emp = pointageList.stream()
                .map(Pointage::getId)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(emp);

        ServicePaie spaie = new ServicePaie();
        for (int i : emp) {
            long nbjr = pointageList.stream()
                    .map(Pointage::getId)
                    .filter(e->e.equals(i))
                    .count();
            System.out.println(nbjr);
            String mois = pointageList.get(0).getDate().toLocalDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            System.out.println(mois);
//            long To


        }
}
}