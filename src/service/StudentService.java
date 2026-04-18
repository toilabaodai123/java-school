package service;

import dto.CreateStudentDTO;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.StudentRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private static int instanceCounter = 0;
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        ++instanceCounter;
        logger.info("Student Service Constructor  {}",instanceCounter);
        this.studentRepository = studentRepository;
    }

    public Student createStudent(CreateStudentDTO createStudentDTO) throws SQLException {
        Student student = new Student();
        student.setCode(createStudentDTO.getCode());
        student.setName(createStudentDTO.getName());
        student.setEmail(createStudentDTO.getEmail());

        return studentRepository.createStudent(student);
    }

    public List<Student> getStudentList() throws SQLException {
        return studentRepository.getAllStudents();
    }

    public Optional<Student> getStudentByCode(String code) throws SQLException {
        return studentRepository.getStudentByCode(code);
    }
}
