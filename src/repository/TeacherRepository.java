package repository;

import database.DatabaseConnection;
import model.Student;
import model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepository {
    private static final Logger logger = LoggerFactory.getLogger(TeacherRepository.class);
    private static int instanceCounter = 0;

    public TeacherRepository(){
        ++instanceCounter;
        logger.info("TeacherRepository constructor {}",instanceCounter);
    }

    public List<Teacher> getAllTeachers() throws SQLException {
        List<Teacher> teacherList = new ArrayList<>();
        String query = "SELECT * FROM teachers";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(query)){
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Teacher teacher = new Teacher();
                teacher.setId(rs.getLong("id"));
                teacher.setCode(rs.getString("code"));
                teacher.setName(rs.getString("name"));

                teacherList.add(teacher);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return teacherList;
    }

    public Teacher createTeacher(Teacher teacher) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO teachers (code, name) VALUES (?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, teacher.getCode());
            pstmt.setString(2, teacher.getName());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                teacher.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teacher;
    }
}
