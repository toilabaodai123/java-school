package repository;

import database.DatabaseConnection;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Class;
import model.Teacher;

public class ClassRepository {
    public List<Class> getClasses() throws SQLException {
        List<Class> classList = new ArrayList<>();
        String query = "SELECT * FROM classes";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(query)){
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Class clazz = new Class();
                clazz.setId(rs.getLong("id"));
                clazz.setCode(rs.getString("code"));

                classList.add(clazz);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return classList;
    }

    public Class createClass(Class clazz) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO classes (code) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, clazz.getCode());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                clazz.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clazz;
    }

    public Class getClassByCode(String code) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM classes WHERE code = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, code);
        ResultSet rs = pstmt.executeQuery();
        Class clazz = new Class();
        while(rs.next()){
            clazz.setId(rs.getLong("id"));
            clazz.setCode(rs.getString("code"));
        }
        return clazz;
    }

    public void addStudentToClass(Class clazz, Student student) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO class_students (classCode, studentCode) VALUES (?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, clazz.getCode());
        pstmt.setString(2, student.getCode());
        pstmt.executeUpdate();
    }

    public void setTeacherToClass(Class clazz, Teacher teacher) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "UPDATE classes SET teacher_code = ? WHERE code = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, teacher.getCode());
        pstmt.setString(2, clazz.getCode());
        pstmt.executeUpdate();
    }

    public List<Student> getClassStudents(Class clazz) throws SQLException {
        List<Student> studentList = new ArrayList<>();
        String query = """
                SELECT s.* FROM students s
                INNER JOIN class_students cs ON s.code = cs.studentCode
                WHERE cs.classCode = ?
                """;
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, clazz.getCode());
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            Student student = new Student(
                    rs.getString("name"),
                    rs.getString("code"),
                    rs.getString("email")
            );
            studentList.add(student);
        }
        return studentList;
    }

    public void removeStudentFromClass(Class clazz, Student student) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "DELETE FROM class_students WHERE classCode = ? AND studentCode = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, clazz.getCode());
        pstmt.setString(2, student.getCode());
        pstmt.executeUpdate();
    }
}
