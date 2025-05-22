package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class StudentMenu {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void displayMenu(Scanner scanner, StudentDao studentDao) {
        while (true) {
            System.out.println("\n===== 📚 Student Menu =====");
            System.out.println("1. ➕ Add Student");
            System.out.println("2. 📋 View All Students");
            System.out.println("3. ✏️ Update Student");
            System.out.println("4. 🗑️ Delete Student");
            System.out.println("5. 🔙 Back to Main Menu");
            System.out.print("👉 Your choice: ");
            int choice = getValidatedIntInput(scanner);

            switch (choice) {
                case 1 -> addStudent(scanner, studentDao);
                case 2 -> viewAllStudents(studentDao);
                case 3 -> updateStudent(scanner, studentDao);
                case 4 -> deleteStudent(scanner, studentDao);
                case 5 -> {
                    System.out.println("🔙 Returning to main menu...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void addStudent(Scanner scanner, StudentDao studentDao) {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        LocalDate dob = getValidatedDateInput(scanner, "Enter date of birth (dd/MM/yyyy): ");

        studentDao.add(new Student(firstName, lastName, email, dob));
        System.out.println("✅ Student added successfully.");
    }

    private static void viewAllStudents(StudentDao studentDao) {
        List<Student> students = studentDao.getAll();
        if (students.isEmpty()) {
            System.out.println("📭 No students found.");
        } else {
            System.out.println("📋 All Students:");
            students.forEach(System.out::println);
        }
    }

    private static void updateStudent(Scanner scanner, StudentDao studentDao) {
        System.out.print("Enter student ID to update: ");
        int id = getValidatedIntInput(scanner);

        Student student = studentDao.getById(id);
        if (student == null) {
            System.out.println("⚠️ Student not found.");
            return;
        }

        System.out.print("Enter new first name: ");
        student.setFirstName(scanner.nextLine().trim());

        System.out.print("Enter new last name: ");
        student.setLastName(scanner.nextLine().trim());

        System.out.print("Enter new email: ");
        student.setEmail(scanner.nextLine().trim());

        LocalDate newDob = getValidatedDateInput(scanner, "Enter new date of birth (dd/MM/yyyy): ");
        student.setDateOfBirth(newDob);

        studentDao.update(student);
        System.out.println("✅ Student updated successfully.");
    }

    private static void deleteStudent(Scanner scanner, StudentDao studentDao) {
        System.out.print("Enter student ID to delete: ");
        int id = getValidatedIntInput(scanner);

        studentDao.delete(id);
        System.out.println("✅ Student deleted successfully.");
    }

    private static int getValidatedIntInput(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid input. Please enter a number: ");
            }
        }
    }

    private static LocalDate getValidatedDateInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format. Please use dd/MM/yyyy.");
            }
        }
    }
}
