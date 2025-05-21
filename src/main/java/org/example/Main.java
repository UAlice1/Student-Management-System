package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        databaseDAO<students> studentsDAO = new StudentDaoImpl();
        databaseDAO<courses> coursesDAO = new coursesDao();
        databaseDAO<marks> marksDAO = new marksDao();

        boolean runApp = true;

        while (runApp) {
            System.out.println("\n=== Welcome to the Student Management System ===");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Manage Marks");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    StudentMenu.displayMenu(scanner, studentsDAO);
                    break;
                case 2:
                    CourseMenu.displayMenu(scanner, coursesDAO);
                    break;
                case 3:
                    MarkMenu.displayMenu(scanner, marksDAO);
                    break;
                case 4:
                    runApp = false;
                    System.out.println("👋 Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("⚠️ Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
