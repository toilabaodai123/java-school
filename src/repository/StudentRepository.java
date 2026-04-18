package repository;

import database.DatabaseConnection;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository {
    private static final Logger logger = LoggerFactory.getLogger(StudentRepository.class);
    private static int instanceCounter = 0;

    public StudentRepository(){
        ++instanceCounter;
        logger.info("StudentRepository constructor {}",instanceCounter);
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        String query = "SELECT * FROM students";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(query)){
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getLong("id"));
                student.setCode(rs.getString("code"));
                student.setName(rs.getString("name"));

                studentList.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return studentList;
    }

    public Student createStudent(Student student) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO students (code, name, email) VALUES (?, ?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, student.getCode());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getEmail());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                student.setId(keys.getLong(1));
            }
            student.setCode(student.getCode());
            student.setName(student.getName());
            student.setEmail(student.getEmail());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    public Optional<Student> getStudentByCode(String code) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM students WHERE code = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, code);
        ResultSet rs = pstmt.executeQuery();
        Student student = null;
        if(rs.next()){
            student = new Student(rs.getString("name"), rs.getString("code"), rs.getString("email"));
            student.setId(rs.getLong("id"));
        }
        return Optional.ofNullable(student);
    }
}
