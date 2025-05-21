package org.example;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CourseMenu {
    public static void displayMenu(Scanner scanner, databaseDAO<courses> courseDAO) {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Course Menu ---");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Get Course by ID");
            System.out.println("4. Update Course");
            System.out.println("5. Delete Course");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addCourse(scanner, courseDAO);
                    break;
                case 2:
                    viewAllCourses(courseDAO);
                    break;
                case 3:
                    getCourseById(scanner, courseDAO);
                    break;
                case 4:
                    updateCourse(scanner, courseDAO);
                    break;
                case 5:
                    deleteCourse(scanner, courseDAO);
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addCourse(Scanner scanner, databaseDAO<courses> courseDAO) {
        System.out.println("Enter course name: ");
        String courseName = scanner.nextLine();

        courses course = new courses(courseName);
        courseDAO.add(course);
    }

    private static void viewAllCourses(databaseDAO<courses> courseDAO) {
        List<courses> courseList = courseDAO.getAll();
        for (courses c : courseList) {
            System.out.println(c);
        }
    }

    private static void getCourseById(Scanner scanner, databaseDAO<courses> courseDAO) {
        System.out.println("Enter course ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            courses course = courseDAO.getById(id);
            if (course != null) {
                System.out.println(course);
            } else {
                System.out.println("Course not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateCourse(Scanner scanner, databaseDAO<courses> courseDAO) {
        System.out.println("Enter course ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            courses course = courseDAO.getById(id);
            if (course == null) {
                System.out.println("Course not found.");
                return;
            }

            System.out.println("Enter new course name: ");
            String courseName = scanner.nextLine();

            courses updated = new courses(courseName);
            updated.setId(id);  // make sure you set the ID
            courseDAO.update(updated);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteCourse(Scanner scanner, databaseDAO<courses> courseDAO) {
        System.out.println("Enter course ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        courseDAO.delete(id);
    }
}
