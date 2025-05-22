package org.example;

import java.util.List;
import java.util.Scanner;

public class MarkMenu {
    public static void displayMenu(Scanner scanner, DatabaseDAO<Mark> markDao) {
        boolean back = false;

        while (!back) {
            System.out.println("--- Marks Menu ---");
            System.out.println("1. Add Mark");
            System.out.println("2. View All Marks");
            System.out.println("3. Get Mark by Student & Course ID");
            System.out.println("4. Update Mark");
            System.out.println("5. Delete Mark");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getValidatedIntInput(scanner, "Enter your choice: "); // prompt added

            switch (choice) {
                case 1 -> addMark(scanner, markDao);
                case 2 -> viewAllMarks(markDao);
                case 3 -> getMarkByStudentAndCourse(scanner, markDao);
                case 4 -> updateMark(scanner, markDao);
                case 5 -> deleteMark(scanner, markDao);
                case 6 -> {
                    back = true;
                    System.out.println(" Returning to main menu...");
                }
                default -> System.out.println(" Invalid choice. Please try again.");
            }
        }
    }

    private static void addMark(Scanner scanner, DatabaseDAO<Mark> markDao) {
        try {
            int studentId = getValidatedIntInput(scanner, "Enter student ID: ");
            int courseId = getValidatedIntInput(scanner, "Enter course ID: ");
            float value = getValidatedFloatInput(scanner, "Enter mark value: ");

            Mark mark = new Mark(studentId, courseId, value);
            markDao.add(mark);
            System.out.println(" Mark added successfully!");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private static void viewAllMarks(DatabaseDAO<Mark> markDao) {
        List<Mark> marksList = markDao.getAll();
        if (marksList.isEmpty()) {
            System.out.println(" No marks found.");
        } else {
            marksList.forEach(System.out::println);
        }
    }

    private static void getMarkByStudentAndCourse(Scanner scanner, DatabaseDAO<Mark> markDao) {
        try {
            int studentId = getValidatedIntInput(scanner, "Enter student ID: ");
            int courseId = getValidatedIntInput(scanner, "Enter course ID: ");

            Mark mark = markDao.getById(studentId, courseId);
            System.out.println(mark != null ? "Mark details: " + mark : " Mark not found.");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private static void updateMark(Scanner scanner, DatabaseDAO<Mark> markDao) {
        try {
            int studentId = getValidatedIntInput(scanner, "Enter student ID: ");
            int courseId = getValidatedIntInput(scanner, "Enter course ID: ");

            Mark existingMark = markDao.getById(studentId, courseId);
            if (existingMark == null) {
                System.out.println("Mark not found.");
                return;
            }

            float newValue = getValidatedFloatInput(scanner, "Enter new mark value: ");
            Mark updatedMark = new Mark(studentId, courseId, newValue);
            markDao.update(updatedMark);
            System.out.println(" Mark updated successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteMark(Scanner scanner, DatabaseDAO<Mark> markDao) {
        try {
            int studentId = getValidatedIntInput(scanner, "Enter student ID: ");
            int courseId = getValidatedIntInput(scanner, "Enter course ID: ");

            markDao.delete(studentId, courseId);
            System.out.println(" Mark deleted successfully!");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private static int getValidatedIntInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print(" Invalid input. Please enter a valid number: ");
            }
        }
    }

    private static float getValidatedFloatInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Float.parseFloat(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print(" Invalid input. Please enter a valid decimal value: ");
            }
        }
    }
}
