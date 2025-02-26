package tn.esprit.powerHR.test;

import tn.esprit.powerHR.models.Departement;
import tn.esprit.powerHR.models.Entreprise;
import tn.esprit.powerHR.services.DepartementService;
import tn.esprit.powerHR.services.EntrepriseService;
import tn.esprit.powerHR.utils.MyDataBase;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EntrepriseService entrepriseService = new EntrepriseService();
    private static final DepartementService departementService = new DepartementService();

    public static void main(String[] args) {
        try {
            boolean running = true;
            while (running) {
                System.out.println("\n=== Main Menu ===");
                System.out.println("1. Manage Entreprises");
                System.out.println("2. Manage Departments");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        manageEnterprises();
                        break;
                    case 2:
                        manageDepartments();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } finally {
            scanner.close();
            MyDataBase.closeConnection();
        }
    }

    private static void manageEnterprises() {
        while (true) {
            System.out.println("\n=== Entreprise Management ===");
            System.out.println("1. Add new entreprise");
            System.out.println("2. Show all entreprises");
            System.out.println("3. Update entreprise");
            System.out.println("4. Delete entreprise");
            System.out.println("0. Back to main menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addEntreprise();
                    break;
                case 2:
                    showAllEntreprises();
                    break;
                case 3:
                    updateEntreprise();
                    break;
                case 4:
                    deleteEntreprise();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void manageDepartments() {
        while (true) {
            System.out.println("\n=== Department Management ===");
            System.out.println("1. Add new department");
            System.out.println("2. Show all departments");
            System.out.println("3. Update department");
            System.out.println("4. Delete department");
            System.out.println("0. Back to main menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addDepartment();
                    break;
                case 2:
                    showAllDepartments();
                    break;
                case 3:
                    updateDepartment();
                    break;
                case 4:
                    deleteDepartment();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Entreprise operations
    private static void addEntreprise() {
        try {
            System.out.println("\n=== Add New Entreprise ===");
            System.out.print("Enter name: ");
            String nom = scanner.nextLine();
            System.out.print("Enter sector: ");
            String secteur = scanner.nextLine();
            System.out.print("Enter fiscal registration: ");
            String matricule = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            Entreprise entreprise = new Entreprise(nom, secteur, matricule, email);
            entrepriseService.add(entreprise);
            System.out.println("Entreprise added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding entreprise: " + e.getMessage());
        }
    }

    private static void showAllEntreprises() {
        System.out.println("\n=== All Entreprises ===");
        System.out.println("----------------------------------------");
        System.out.printf("%-5s | %-20s | %-15s | %-20s | %-30s%n", 
            "ID", "Name", "Sector", "Fiscal ID", "Email");
        System.out.println("----------------------------------------");
        
        for (Entreprise e : entrepriseService.getAll()) {
            System.out.printf("%-5d | %-20s | %-15s | %-20s | %-30s%n",
                e.getId(),
                truncateString(e.getNom(), 20),
                truncateString(e.getSecteur(), 15),
                truncateString(e.getMatriculeFiscale(), 20),
                truncateString(e.getEmail(), 30)
            );
        }
        System.out.println("----------------------------------------");
    }

    // Helper method to truncate long strings
    private static String truncateString(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }

    private static void updateEntreprise() {
        System.out.println("\n=== Update Entreprise ===");
        System.out.print("Enter entreprise ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Entreprise entreprise = entrepriseService.getById(id);
        if (entreprise == null) {
            System.out.println("Entreprise not found!");
            return;
        }

        System.out.print("Enter new name (current: " + entreprise.getNom() + "): ");
        String nom = scanner.nextLine();
        System.out.print("Enter new sector (current: " + entreprise.getSecteur() + "): ");
        String secteur = scanner.nextLine();
        System.out.print("Enter new fiscal registration (current: " + entreprise.getMatriculeFiscale() + "): ");
        String matricule = scanner.nextLine();
        System.out.print("Enter new email (current: " + entreprise.getEmail() + "): ");
        String email = scanner.nextLine();

        entreprise.setNom(nom);
        entreprise.setSecteur(secteur);
        entreprise.setMatriculeFiscale(matricule);
        entreprise.setEmail(email);

        entrepriseService.update(entreprise);
        System.out.println("Entreprise updated successfully!");
    }

    private static void deleteEntreprise() {
        System.out.println("\n=== Delete Entreprise ===");
        System.out.print("Enter entreprise ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        entrepriseService.delete(id);
        System.out.println("Entreprise deleted successfully!");
    }

    // Department operations
    private static void addDepartment() {
        System.out.println("\n=== Add New Department ===");
        System.out.print("Enter name: ");
        String nom = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        Departement departement = new Departement(nom, description, 0);
        departementService.add(departement);
        System.out.println("Department added successfully!");
    }

    private static void showAllDepartments() {
        System.out.println("\n=== All Departments ===");
        departementService.getAll().forEach(System.out::println);
    }

    private static void updateDepartment() {
        System.out.println("\n=== Update Department ===");
        System.out.print("Enter department ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Departement departement = departementService.getById(id);
        if (departement == null) {
            System.out.println("Department not found!");
            return;
        }

        System.out.print("Enter new name (current: " + departement.getNom() + "): ");
        String nom = scanner.nextLine();
        System.out.print("Enter new description (current: " + departement.getDescription() + "): ");
        String description = scanner.nextLine();

        departement.setNom(nom);
        departement.setDescription(description);

        departementService.update(departement);
        System.out.println("Department updated successfully!");
    }

    private static void deleteDepartment() {
        System.out.println("\n=== Delete Department ===");
        System.out.print("Enter department ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        departementService.delete(id);
        System.out.println("Department deleted successfully!");
    }
}