package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoursesDao implements DatabaseDAO<Course> {

    private static final Logger logger = Logger.getLogger(CoursesDao.class.getName());

    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/Student_management";
        String user = "postgres";
        String password = "Alice@123";
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void add(Course course) {
        String sql = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getCourseDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding course", e);
        }
    }

    @Override
    public Course getById(int id) {
        String sql = "SELECT * FROM courses WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Course(
                        rs.getInt("id"),
                        rs.getString("course_name"),
                        rs.getString("course_description")
                );
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching course by id", e);
        }
        return null;
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("course_name"),
                        rs.getString("course_description")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching all courses", e);
        }
        return courses;
    }

    @Override
    public void update(Course course) {
        String sql = "UPDATE courses SET course_name = ?, course_description = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getCourseDescription());
            stmt.setInt(3, course.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating course", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting course", e);
        }
    }

    // Since Courses only have single key, these composite key methods are not applicable.
    // You must implement them due to the interface, but you can throw exception or return null.

    @Override
    public Course getById(int id1, int id2) {
        throw new UnsupportedOperationException("getById(int, int) not supported in CoursesDao");
    }

    @Override
    public void delete(int id1, int id2) {
        throw new UnsupportedOperationException("delete(int, int) not supported in CoursesDao");
    }
}
