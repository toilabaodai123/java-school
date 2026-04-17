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

    public Student createStudent(CreateStudentDTO createStudentDTO) throws SQLException {
        StudentRepository studentRepository = new StudentRepository();
        Student student = new Student();
        student.setCode(createStudentDTO.getCode());
        student.setName(createStudentDTO.getName());
        student.setEmail(createStudentDTO.getEmail());

        return studentRepository.createStudent(student);
    }

    public List<Student> getStudentList() throws SQLException {
        StudentRepository studentRepository = new StudentRepository();
        return studentRepository.getAllStudents();
    }

    public Optional<Student> getStudentByCode(String code) throws SQLException {
        StudentRepository studentRepository = new StudentRepository();
        return studentRepository.getStudentByCode(code);
    }
}
