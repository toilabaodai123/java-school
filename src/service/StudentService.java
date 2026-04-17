package service;

import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public List<Student> studentList = new ArrayList<Student>();

    public Student addStudent(Student student){
        studentList.add(student);
        return student;
    }

    public void deleteStudent(Student student){
        studentList.remove(student);
    }

    public List<Student> getStudentList(){
        return studentList;
    }

    public Optional<Student> getStudentByCode(String code) {
        return studentList.stream()
                .filter(s -> code.equals(s.getCode()))
                .findFirst();
    }
}
