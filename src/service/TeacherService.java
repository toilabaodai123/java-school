package service;

import model.Student;
import model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.TeacherRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherService {
    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    private List<Teacher> teacherList = new ArrayList<Teacher>();

    public List<Teacher> getTeacherList() throws SQLException {
        TeacherRepository teacherRepository = new TeacherRepository();
        return teacherRepository.getAllTeachers();
    }

    public Teacher addTeacher(Teacher teacher) throws SQLException {
        TeacherRepository teacherRepository = new TeacherRepository();
        return teacherRepository.createTeacher(teacher);
    }
}
