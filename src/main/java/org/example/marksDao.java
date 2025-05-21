package org.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class marksDao implements databaseDAO<marks> {
    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/Student_management","postgres","Alice@123");

    }

    @Override
    public void add(marks marks) {
        String sql="INSERT INTO marks (student_id,course_id,marks) VALUES (?,?,?)";
        try(Connection conn = getConnection(); PreparedStatement stmt=conn.prepareStatement(sql)){
            stmt.setInt(1,marks.getStudentId());
            stmt.setInt(2,marks.getCourseId());
            stmt.setFloat(3,marks.getMarks());
            stmt.executeUpdate();
            System.out.println("mark inserted ");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public marks getById(int id) {
        String sql = "SELECT * FROM marks WHERE mark_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                new marks(

                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getFloat("marks")
                );
            } else {
                System.out.println(" No mark found with ID " + id);
                return null;
            }
        } catch (SQLException e) {
            System.out.println(" Error retrieving mark: " + e.getMessage());
            return null;
        }
    }

    @Override

        public List<marks> getAll() {
            List<marks> marksList = new ArrayList<>();
            String sql = "SELECT * FROM marks";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    marks mark = new marks(

                            rs.getInt("student_id"),
                            rs.getInt("course_id"),
                            rs.getInt("marks")

                    );
                    marksList.add(mark);
                }
            } catch (SQLException e) {
                System.out.println(" Error getting marks: " + e.getMessage());
            }
            return marksList;
        }
    }

    @Override
    public void update(marks marks) {
        String sql = "UPDATE marks SET student_id = ?, course_id = ?, marks= ? WHERE student_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, marks.getStudentId());
            stmt.setInt(2, marks.getCourseId());
            stmt.setDouble(3, marks.getMarks());
            stmt.executeUpdate();
            System.out.println("Mark updated.");
        } catch (SQLException e) {
            System.out.println(" Error updating mark: " + e.getMessage());
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM marks WHERE student_id=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println(" Mark deleted.");
        } catch (SQLException e) {
            System.out.println(" Error deleting mark: " + e.getMessage());
        }
    }

