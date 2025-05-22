package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarksDao implements DatabaseDAO<Mark> {

    private static final Logger logger = Logger.getLogger(MarksDao.class.getName());

    // Removed connection details from fields, now local in getConnection()
    private Connection getConnection() throws SQLException {
        // Connection details as local variables
        String url = "jdbc:postgresql://localhost:5432/Student_management";
        String user = "postgres";
        String password = "Alice@123";
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void add(Mark mark) {
        String sql = "INSERT INTO marks (student_id, course_id, marks) " +
                "VALUES (?, ?, ?) " +
                "ON CONFLICT (student_id, course_id) DO UPDATE SET marks = EXCLUDED.marks";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mark.getStudentId());
            stmt.setInt(2, mark.getCourseId());
            stmt.setFloat(3, mark.getMarks());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding or updating mark", e);
        }
    }

    @Override
    public Mark getById(int studentId, int courseId) {
        String sql = "SELECT * FROM marks WHERE student_id = ? AND course_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Mark(
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getFloat("marks")
                );
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving mark by studentId and courseId", e);
        }
        return null;
    }

    @Override
    public List<Mark> getAll() {
        List<Mark> marks = new ArrayList<>();
        String sql = "SELECT * FROM marks";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                marks.add(new Mark(
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getFloat("marks")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving all marks", e);
        }
        return marks;
    }

    @Override
    public void update(Mark mark) {
        // 'add' method already does insert or update, so reuse it
        add(mark);
    }

    @Override
    public void delete(int studentId, int courseId) {
        String sql = "DELETE FROM marks WHERE student_id = ? AND course_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting mark", e);
        }
    }

    // Not applicable methods for this DAO, just implement empty or return null

    @Override
    public Mark getById(int id) {
        // Not applicable for composite key table, returning null
        return null;
    }

    @Override
    public void delete(int id) {
        // Not applicable for composite key table, do nothing
    }
}
