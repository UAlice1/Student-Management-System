package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements databaseDAO<students> {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/Student_management", "postgres", "Alice@123"
        );
    }

    @Override
    public void add(students student) {
        String sql = "INSERT INTO students (student_firstname, student_lastname, student_email, date_of_birth) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getstudent_firstname());
            stmt.setString(2, student.getstudent_lastname());
            stmt.setString(3, student.getStudent_email());
            stmt.setDate(4, student.getDate_of_birth());
            stmt.executeUpdate();
            System.out.println("✅ Student added successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Failed to add student: " + e.getMessage());
        }
    }

    @Override
    public students getById(int id) throws SQLException {
        students student = null;
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                student = new students(
                        rs.getInt("student_id"),
                        rs.getString("student_firstname"),
                        rs.getString("student_lastname"),
                        rs.getString("student_email"),
                        rs.getDate("date_of_birth")
                );
            }
        }
        return student;
    }

    @Override
    public List<students> getAll() {
        List<students> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students student = new students(
                        rs.getInt("student_id"),
                        rs.getString("student_firstname"),
                        rs.getString("student_lastname"),
                        rs.getString("student_email"),
                        rs.getDate("date_of_birth")
                );
                list.add(student);
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch students: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void update(students student) {
        String sql = "UPDATE students SET student_firstname = ?, student_lastname = ?, student_email = ?, date_of_birth = ? WHERE student_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getstudent_firstname());
            stmt.setString(2, student.getstudent_lastname());
            stmt.setString(3, student.getStudent_email());
            stmt.setDate(4, student.getDate_of_birth());
            stmt.setInt(5, student.getStudent_id());
            stmt.executeUpdate();
            System.out.println("✅ Student updated successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to update student: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("✅ Student deleted successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Failed to delete student: " + e.getMessage());
        }
    }
}
