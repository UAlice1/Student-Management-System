package org.example;

import java.util.List;
import java.util.Scanner;

public class CourseMenu {
    private static int courseId;

    public static void displayMenu(Scanner scanner, DatabaseDAO<Course> courseDAO) {
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

    private static void addCourse(Scanner scanner, DatabaseDAO<Course> courseDAO) {
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();

        System.out.print("Enter course description: ");
        String courseDescription = scanner.nextLine();

        Course course = new Course(courseId, courseName, courseDescription);
        courseDAO.add(course);
        System.out.println("✅ Course added successfully.");
    }

    private static void viewAllCourses(DatabaseDAO<Course> courseDAO) {
        List<Course> courseList = courseDAO.getAll();
        if (courseList.isEmpty()) {
            System.out.println("⚠️ No courses found.");
        } else {
            System.out.println("--- All Courses ---");
            for (Course c : courseList) {
                System.out.println(c);
            }
        }
    }

    private static void getCourseById(Scanner scanner, DatabaseDAO<Course> courseDAO) {
        System.out.print("Enter course ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Course course = courseDAO.getById(id);
        if (course != null) {
            System.out.println("✅ Course found:");
            System.out.println(course);
        } else {
            System.out.println("⚠️ Course not found.");
        }
    }

    private static void updateCourse(Scanner scanner, DatabaseDAO<Course> courseDAO) {
        System.out.print("Enter course ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        Course course = courseDAO.getById(id);
        if (course == null) {
            System.out.println("⚠️ Course not found.");
            return;
        }

        System.out.print("Enter new course name: ");
        String courseName = scanner.nextLine();

        System.out.print("Enter new course description: ");
        String courseDescription = scanner.nextLine();

        Course updated = new Course(id, courseName, courseDescription);
        courseDAO.update(updated);
        System.out.println("✅ Course updated successfully.");
    }

    private static void deleteCourse(Scanner scanner, DatabaseDAO<Course> courseDAO) {
        System.out.print("Enter course ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        courseDAO.delete(id);
        System.out.println("🗑️ Course deleted successfully.");
    }
}
