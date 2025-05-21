package org.example;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    public static void displayMenu(Scanner scanner, databaseDAO<students> studentDAO) {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Get Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addStudent(scanner, studentDAO);
                    break;
                case 2:
                    viewAllStudents(studentDAO);
                    break;
                case 3:
                    getStudentById(scanner, studentDAO);
                    break;
                case 4:
                    updateStudent(scanner, studentDAO);
                    break;
                case 5:
                    deleteStudent(scanner, studentDAO);
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addStudent(Scanner scanner, databaseDAO<students> studentDAO) {
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        System.out.println("Enter date of birth (yyyy-mm-dd): ");
        String dob = scanner.nextLine();

        students student = new students(firstName, lastName, email, java.sql.Date.valueOf(dob));
        studentDAO.add(student);
    }

    private static void viewAllStudents(databaseDAO<students> studentDAO) {
        List<students> studentsList = studentDAO.getAll();
        for (students s : studentsList) {
            System.out.println(s);
        }
    }

    private static void getStudentById(Scanner scanner, databaseDAO<students> studentDAO) {
        System.out.println("Enter student ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            students student = studentDAO.getById(id);
            if (student != null) {
                System.out.println(student);
            } else {
                System.out.println("No student found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateStudent(Scanner scanner, databaseDAO<students> studentDAO) {
        System.out.println("Enter student ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            students student = studentDAO.getById(id);
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            System.out.println("Enter new first name: ");
            String firstName = scanner.nextLine();
            System.out.println("Enter new last name: ");
            String lastName = scanner.nextLine();
            System.out.println("Enter new email: ");
            String email = scanner.nextLine();
            System.out.println("Enter new date of birth (yyyy-mm-dd): ");
            String dob = scanner.nextLine();

            students updated = new students(firstName, lastName, email, java.sql.Date.valueOf(dob));
            studentDAO.update(updated);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteStudent(Scanner scanner, databaseDAO<students> studentDAO) {
        System.out.println("Enter student ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        studentDAO.delete(id);
    }
}
