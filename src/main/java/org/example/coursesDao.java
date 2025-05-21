package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class coursesDao implements databaseDAO<courses> {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/Student_management",
                "postgres",
                "Alice@123"
        );
    }


    @Override
    public void add(courses course) {
        String sql = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourse_name());
            stmt.setString(2, course.getCourse_description());
            stmt.executeUpdate();
            System.out.println("Course inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting course: " + e.getMessage());
        }
    }


    @Override
    public courses getById(int id) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int courseId = resultSet.getInt("course_id");
                String courseName = resultSet.getString("course_name");
                String courseDescription = resultSet.getString("course_description");
                return new courses(courseId, courseName, courseDescription);
            } else {
                System.out.println(" Course with ID " + id + " not found.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching course: " + e.getMessage());
            return null;
        }
    }


    @Override
    public List<courses> getAll() {
        List<courses> list = new ArrayList<>();
        String sql = "SELECT * FROM courses";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                String courseName = rs.getString("course_name");
                String courseDescription = rs.getString("course_description");
                courses course = new courses(courseId, courseName, courseDescription);
                list.add(course);
            }
        } catch (SQLException e) {
            System.out.println(" Error retrieving all courses: " + e.getMessage());
        }

        return list;
    }


    @Override
    public void update(courses course) {
        String sql = "UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourse_name());
            stmt.setString(2, course.getCourse_description());

            stmt.executeUpdate();
            System.out.println("Course updated successfully.");
        } catch (SQLException e) {
            System.out.println(" Error updating course: " + e.getMessage());
        }
    }

    @Override
    public void delete(int course_id) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, course_id);
            stmt.executeUpdate();
            System.out.println("Course deleted successfully.");
        } catch (SQLException e) {
            System.out.println(" Error deleting course: " + e.getMessage());
        }
    }
}
