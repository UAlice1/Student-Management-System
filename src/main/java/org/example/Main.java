package org.example;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDao studentDao = new StudentDao();
    private static final CoursesDao coursesDao = new CoursesDao();
    private static final MarksDao marksDao = new MarksDao();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Manage Marks");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> studentMenu();
                case 2 -> courseMenu();
                case 3 -> marksMenu();
                case 4 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void studentMenu() {
        StudentMenu.displayMenu(scanner, studentDao);
    }

    private static void courseMenu() {
        while (true) {
            System.out.println("\n--- Course Menu ---");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Update Course");
            System.out.println("4. Delete Course");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Course name: ");
                    String name = scanner.nextLine();
                    System.out.print("Description: ");
                    String desc = scanner.nextLine();
                    coursesDao.add(new Course(name, desc));
                    System.out.println("✅ Course added.");
                }
                case 2 -> {
                    var courses = coursesDao.getAll();
                    System.out.println("All Courses:");
                    courses.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Enter course ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Course c = coursesDao.getById(id);
                    if (c == null) {
                        System.out.println("Course not found.");
                    } else {
                        System.out.print("New name: ");
                        c.setCourseName(scanner.nextLine());
                        System.out.print("New description: ");
                        c.setCourseDescription(scanner.nextLine());
                        coursesDao.update(c);
                        System.out.println("✅ Course updated.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter course ID to delete: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    coursesDao.delete(id);
                    System.out.println("✅ Course deleted.");
                }
                case 5 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void marksMenu() {
        while (true) {
            System.out.println("\n--- Marks Menu ---");
            System.out.println("1. Add/Update Mark");
            System.out.println("2. View All Marks");
            System.out.println("3. View Mark by Student & Course");
            System.out.println("4. Delete Mark");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Student ID: ");
                    int studentId = scanner.nextInt();
                    System.out.print("Course ID: ");
                    int courseId = scanner.nextInt();
                    System.out.print("Marks: ");
                    float mark = scanner.nextFloat();
                    scanner.nextLine();
                    marksDao.add(new Mark(studentId, courseId, mark));
                    System.out.println("✅ Mark saved.");
                }
                case 2 -> {
                    var marks = marksDao.getAll();
                    System.out.println("All Marks:");
                    marks.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Student ID: ");
                    int studentId = scanner.nextInt();
                    System.out.print("Course ID: ");
                    int courseId = scanner.nextInt();
                    scanner.nextLine();
                    Mark m = marksDao.getById(studentId, courseId);
                    System.out.println(m != null ? m : "Mark not found.");
                }
                case 4 -> {
                    System.out.print("Student ID: ");
                    int studentId = scanner.nextInt();
                    System.out.print("Course ID: ");
                    int courseId = scanner.nextInt();
                    scanner.nextLine();
                    marksDao.delete(studentId, courseId);
                    System.out.println("✅ Mark deleted.");
                }
                case 5 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
