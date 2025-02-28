package tn.esprit.powerHR.controllers;

import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import tn.esprit.powerHR.controllers.enums.Poste;
import tn.esprit.powerHR.models.Employe;
import tn.esprit.powerHR.models.Paie;
import tn.esprit.powerHR.services.ServiceScraping;
import tn.esprit.powerHR.services.ServicePaie;
import tn.esprit.powerHR.services.ServicePointage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FichePaieController {

    @FXML
    private Button bt_calc;

    @FXML
    private Button bt_download;

    @FXML
    private ListView<Paie> lv_paie;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField tf_nbrenf;

    @FXML
    private TextField tf_salnet;

    @FXML
    private TextField tf_search_paie;

    @FXML
    private Text tx_re;

    @FXML
    private Text tx_sb;

    @FXML
    private Text st_cnss;

    @FXML
    private Text st_sb;

    ObservableList<Paie> observableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            ServicePaie sp = new ServicePaie();
            ServicePointage spointage = new ServicePointage();
            List<Integer> listIdPointage = spointage.getAll().stream()
                    .filter(p -> p.getEmploye().getId() == 1) //supposons aziz d'id 1 est connecté
                    .map(e->e.getPaie().getId())
                    .distinct()
                    .toList();
            List<Paie> paies = new ArrayList<>();
            for (Integer i : listIdPointage) {
                paies.add(sp.getAll().stream()
                        .filter(p -> p.getId() == i)
                        .findFirst()
                        .get());
            }

            observableList = FXCollections.observableArrayList(paies);

            lv_paie.setItems(observableList);

            lv_paie.setCellFactory(param -> new ListCell<Paie>() {
                @Override
                protected void updateItem(Paie paie, boolean empty) {
                    super.updateItem(paie, empty);

                    if (empty || paie == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox();
                        hbox.setSpacing(10);

                        Text nbrjr = new Text(String.valueOf(paie.getNbjour()));
                        Text total = new Text(String.valueOf(paie.getMontant()));
                        Text mois = new Text(paie.getMois());
                        Text annee = new Text(paie.getAnnee());

                        nbrjr.setWrappingWidth(200);
                        total.setWrappingWidth(135);
                        mois.setWrappingWidth(55);
                        annee.setWrappingWidth(40);

                        Button pointageButton = new Button();
                        pointageButton.setStyle("-fx-background-color: #eeeeee; -fx-cursor: hand;");
                        pointageButton.setText("Afficher Pointage");

                        pointageButton.setOnAction(event -> {
                            try {

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PointageEmp.fxml"));
                                Parent statView = loader.load();
                                PointageEmpController controller = loader.getController();
                                System.out.println("ID1: " + paie.getId());
                                controller.setLv_pointage(paie.getId());

                                mainPane.getChildren().setAll(statView);
                            } catch (IOException e) {
                                System.err.println("Error loading " + e.getMessage());
                            }
                        });
                        hbox.getChildren().addAll(nbrjr, total, mois, annee, pointageButton);
                        setGraphic(hbox);
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    @FXML
    void download(ActionEvent event) {
// Supposons que l'employé connecté est Aziz Hamlaoui
        Employe emp = new Employe(1, "Aziz Hamlaoui", "Aziz", Poste.Directeur, 5000.00, null, null, null, null, null, null, null);        String downloadsPath = System.getProperty("user.home") + "/Downloads/";
        String dest = downloadsPath + "Fiche De Paie de " + emp.getUsername() + ".pdf";

        try {
            // Create PDF document
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Title: "Fiche De Paie"
            Paragraph title = new Paragraph("Fiche De Paie")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20)
                    .setBold();
            document.add(title);

            Paragraph p = new Paragraph("Nom: " + emp.getUsername()).setBold();
            Paragraph p1 = new Paragraph("Id Employé: " + emp.getId()).setFontSize(10);
            Paragraph p2 = new Paragraph("Poste: " + emp.getPoste()).setFontSize(10);
            Paragraph p3 = new Paragraph("Salaire: " + emp.getSalaire()).setFontSize(10);
            Paragraph p4 = new Paragraph("");

//            // Employee Info Box
//            Table empTable = new Table(4);
//            empTable.addCell(new Cell().add("Nom: ").setBold());
//            empTable.addCell(new Cell().add("Id Employé: "));
//            empTable.addCell(new Cell().add(""));
//            empTable.addCell(new Cell().add(""));
//            empTable.addCell(new Cell().add("Poste: "));
//            empTable.addCell("Salaire: ");
//            empTable.setBorder(new SolidBorder(new DeviceRgb(0, 0, 255),1));
//            empTable.setMarginBottom(10);
            document.add(p);
            document.add(p1);
            document.add(p2);
            document.add(p3);
            document.add(p4);
            document.add(p4);
            document.add(p4);
            document.add(p4);

            // Payslip Table
            Table table = new Table(new float[]{4, 2, 2});
            table.setWidthPercent(100);

            // Table Header
            table.addCell(new Cell().add("Mois").setBold());
            table.addCell(new Cell().add("Salaire").setBold());
            table.addCell(new Cell().add("Nombre Du Jour").setBold());

            // Table Data
            for (Paie pa : observableList) {
                table.addCell(pa.getMois() + " " + pa.getAnnee());
                table.addCell(String.valueOf(pa.getMontant()));
                table.addCell(String.valueOf(pa.getNbjour()));
            }

            document.add(table);

            // Close document
            document.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Génération PDF");
            alert.setHeaderText(null);
            alert.setContentText("La fiche de paie a été générée avec succès et sauvegardée dans:\n" + dest);
            alert.showAndWait();
            System.out.println("Payslip PDF created: " + dest);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void search(KeyEvent event) {
        try {
            ServicePaie sp = new ServicePaie();
            ServicePointage spointage = new ServicePointage();
            List<Integer> listIdPointage = spointage.getAll().stream()
                    .filter(p -> p.getEmploye().getId() == 1) //supposons aziz d'id 1 est connecté
                    .map(e->e.getPaie().getId())
                    .distinct()
                    .toList();
            List<Paie> paies = new ArrayList<>();
            for (Integer i : listIdPointage) {
                if (sp.getAll().stream()
                        .filter(p -> p.getId() == i)
                        .findFirst()
                        .get().getMois().toLowerCase().contains(tf_search_paie.getText().toLowerCase())) {
                    paies.add(sp.getAll().stream()
                            .filter(p -> p.getId() == i)
                            .findFirst()
                            .get());
                }
            }

            observableList = FXCollections.observableArrayList(paies);

            lv_paie.setItems(observableList);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void Calc(ActionEvent event) {
        ServiceScraping srvApi = new ServiceScraping();
        List<String> list = srvApi.automatePaieTunisie(tf_nbrenf.getText(), tf_salnet.getText());
        st_sb.setText("Salaire Brutte");
        st_cnss.setText("Retenue CNSS");
        tx_re.setText(list.get(0));
        tx_sb.setText(list.get(1));
    }

}