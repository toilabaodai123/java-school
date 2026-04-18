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
    private static int instanceCounter = 0;
    private final TeacherRepository teacherRepository;


    public TeacherService(TeacherRepository teacherRepository) {
        ++instanceCounter;
        logger.info("TeacherService constructor  {}",instanceCounter);
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getTeacherList() throws SQLException {
        return teacherRepository.getAllTeachers();
    }

    public Teacher addTeacher(Teacher teacher) throws SQLException {
        return teacherRepository.createTeacher(teacher);
    }
}
