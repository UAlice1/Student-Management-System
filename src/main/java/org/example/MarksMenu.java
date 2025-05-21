package org.example

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MarkMenu {
    public static void displayMenu(Scanner scanner, databaseDAO<marks> markDAO) {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Marks Menu ---");
            System.out.println("1. Add Mark");
            System.out.println("2. View All Marks");
            System.out.println("3. Get Mark by ID");
            System.out.println("4. Update Mark");
            System.out.println("5. Delete Mark");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addMark(scanner, markDAO);
                    break;
                case 2:
                    viewAllMarks(markDAO);
                    break;
                case 3:
                    getMarkById(scanner, markDAO);
                    break;
                case 4:
                    updateMark(scanner, markDAO);
                    break;
                case 5:
                    deleteMark(scanner, markDAO);
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addMark(Scanner scanner, databaseDAO<marks> markDAO) {
        System.out.println("Enter student ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter course ID: ");
        int courseId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter mark value: ");
        double value = Double.parseDouble(scanner.nextLine());

        marks mark = new marks(studentId, courseId, value);
        markDAO.add(mark);
    }

    private static void viewAllMarks(databaseDAO<marks> markDAO) {
        List<marks> marksList = markDAO.getAll();
        for (marks m : marksList) {
            System.out.println(m);
        }
    }

    private static void getMarkById(Scanner scanner, databaseDAO<marks> markDAO) {
        System.out.println("Enter mark ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            marks mark = markDAO.getById(id);
            if (mark != null) {
                System.out.println(mark);
            } else {
                System.out.println("Mark not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateMark(Scanner scanner, databaseDAO<marks> markDAO) {
        System.out.println("Enter mark ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            marks mark = markDAO.getById(id);
            if (mark == null) {
                System.out.println("Mark not found.");
                return;
            }

            System.out.println("Enter new student ID: ");
            int studentId = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter new course ID: ");
            int courseId = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter new mark value: ");
            double value = Double.parseDouble(scanner.nextLine());

            marks updated = new marks(studentId, courseId, value);
            updated.setId(id);
            markDAO.update(updated);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteMark(Scanner scanner, databaseDAO<marks> markDAO) {
        System.out.println("Enter mark ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        markDAO.delete(id);
    }
}
