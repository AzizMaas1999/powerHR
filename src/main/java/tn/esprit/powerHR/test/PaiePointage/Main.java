package tn.esprit.powerHR.test.PaiePointage;

import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.User.Employe;
import tn.esprit.powerHR.services.User.ServiceEmploye;

import java.util.List;

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
//        ObservableList<Pointage> pointageList = FXCollections.observableArrayList();
//        pointageList.add(new Pointage(1, Date.valueOf("2025-02-01"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
//        pointageList.add(new Pointage(2, Date.valueOf("2025-02-01"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
//        pointageList.add(new Pointage(3, Date.valueOf("2025-02-01"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
//        pointageList.add(new Pointage(1, Date.valueOf("2025-02-02"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
//        pointageList.add(new Pointage(2, Date.valueOf("2025-02-02"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
//        pointageList.add(new Pointage(3, Date.valueOf("2025-02-02"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
//        pointageList.add(new Pointage(1, Date.valueOf("2025-02-03"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
//        pointageList.add(new Pointage(2, Date.valueOf("2025-02-03"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
//        pointageList.add(new Pointage(3, Date.valueOf("2025-02-03"), Time.valueOf("07:58:00"), Time.valueOf("17:10:00"), null, null));
//        List<Integer> emp = pointageList.stream()
//                .map(Pointage::getId)
//                .distinct()
//                .collect(Collectors.toList());
//        System.out.println(emp);
//
//        ServicePaie spaie = new ServicePaie();
//        ServiceEmploye se = new ServiceEmploye();
//        for (int i : emp) {
//            long nbjr = pointageList.stream()
//                    .map(Pointage::getId)
//                    .filter(e->e.equals(i))
//                    .count();
//            System.out.println(nbjr);
//            String mois = pointageList.get(0).getDate().toLocalDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
//            System.out.println(mois);
//
//            float Total =(float) (se.getById(i).getSalaire() / 30) * nbjr;
//            System.out.println(Total);
//        long ret = (Time.valueOf("08:30:00").getTime() - Time.valueOf("08:00:00").getTime())/60000;
//        System.out.println(ret);
//        ServiceScraping srvApi = new ServiceScraping();
//        srvApi.automatePaieTunisie("2","3000");
//        String downloadsPath = System.getProperty("user.home") + "/Downloads/";
//        String dest = downloadsPath + "FicheDePaie.pdf";
//
//        try {
//            // Create PDF document
//            PdfWriter writer = new PdfWriter(dest);
//            PdfDocument pdf = new PdfDocument(writer);
//            Document document = new Document(pdf);
//
//            // Title: "Fiche De Paie"
//            Paragraph title = new Paragraph("Fiche De Paie")
//                    .setTextAlignment(TextAlignment.CENTER)
//                    .setFontSize(20)
//                    .setBold();
//            document.add(title);
//
//            Paragraph p = new Paragraph("Nom: " + ).setBold();
//            Paragraph p1 = new Paragraph("Id Employé: ").setFontSize(10);
//            Paragraph p2 = new Paragraph("Poste: ").setFontSize(10);
//            Paragraph p3 = new Paragraph("Salaire: ").setFontSize(10);
//            Paragraph p4 = new Paragraph("");
//
////            // Employee Info Box
////            Table empTable = new Table(4);
////            empTable.addCell(new Cell().add("Nom: ").setBold());
////            empTable.addCell(new Cell().add("Id Employé: "));
////            empTable.addCell(new Cell().add(""));
////            empTable.addCell(new Cell().add(""));
////            empTable.addCell(new Cell().add("Poste: "));
////            empTable.addCell("Salaire: ");
////            empTable.setBorder(new SolidBorder(new DeviceRgb(0, 0, 255),1));
////            empTable.setMarginBottom(10);
//            document.add(p);
//            document.add(p1);
//            document.add(p2);
//            document.add(p3);
//            document.add(p4);
//            document.add(p4);
//            document.add(p4);
//            document.add(p4);
//
//            // Payslip Table
//            Table table = new Table(new float[]{4, 2, 2});
//            table.setWidthPercent(100);
//
//            // Table Header
//            table.addCell(new Cell().add("Mois").setBold());
//            table.addCell(new Cell().add("Salaire").setBold());
//            table.addCell(new Cell().add("Nombre Du Jour").setBold());
//
//            // Table Data
//            table.addCell("Janvier 2024");
//            table.addCell("3500");
//            table.addCell("25 Jour");
//
//            document.add(table);
//
//            // Close document
//            document.close();
//            System.out.println("Payslip PDF created: " + dest);
//
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }

//        ServiceApi srvApi = new ServiceApi();
//        List<Employe> employes = new ArrayList<>();
//        Employe e = new Employe(1,"fdkbgkndfg","fdkbgkndfg", Poste.Charges,445.2,"123456789125","fdkbgkndfg",null,null,null,null,null);
//        Employe e1 = new Employe(2,"fdkbgkndfg","fdkbgkndfg", Poste.Charges,445.2,"123456789125","fdkbgkndfg",null,null,null,null,null);
//        employes.add(e);
//        employes.add(e1);
//        srvApi.response(employes);
//        String m = String.valueOf(LocalDate.now().getMonthValue());
//        ServicePointage spoi = new ServicePointage();
//        ServicePaie sp = new ServicePaie();
//        List<Integer> IdsPaie = sp.getAll().stream()
//                .filter(p -> p.getMois().equals(LocalDate.now().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH))
//                        && p.getAnnee().equals(String.valueOf(LocalDate.now().getYear())))
//                .filter(p -> p.getAnnee().equals(String.valueOf(LocalDate.now().getYear())))
//                .map(Paie::getId)
//                .toList();
//        List<Employe> employes = new ArrayList<>();
//        for (int id : IdsPaie) {
//            employes.add(spoi.getAll().stream()
//                    .filter(p -> p.getPaie().getId() == id)
//                    .map(Pointage::getEmploye)
//                    .findFirst()
//                    .get());
//        }
//        ServiceApi srvApi = new ServiceApi();
//        srvApi.response(employes);
//        String s = LocalTime.now();

//        Argon2 argon2 = Argon2Factory.create();
//        String hash = argon2.hash(3, 65536, 1, "powerHRadmin");
//        System.out.println(hash);
//        System.out.println(argon2.verify("$argon2i$v=19$m=65536,t=3,p=1$vNoS+KjVJdADtzlaD1Tb4g$4FIpPDkPH44HiayuRG4FvEfE+xJnclbZUx0ItJNCouI", "Aymen"));
        ServiceEmploye se = new ServiceEmploye();
        List<String> passwords = se.getAll().stream()
                .map(Employe::getPassword)
                .toList();
        for (String password : passwords) {
            System.out.println(password);
        }


    }
}