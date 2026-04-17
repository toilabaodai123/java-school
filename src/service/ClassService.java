package service;

import dto.AddStudentToClassDTO;
import dto.CreateClassDTO;
import dto.RemoveStudentFromClassDTO;
import dto.SendAddedClassEmailTaskDTO;
import exception.QueueJobException;
import exception.StudentAlreadyInClassException;
import model.Class;
import model.Student;
import model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.ClassRepository;
import repository.StudentRepository;
import task.quartz.SendAddedClassEmailTask;
import task.TaskManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ClassService {
    private static final Logger logger = LoggerFactory.getLogger(ClassService.class);


    public Class createClass(CreateClassDTO createClassDTO) throws SQLException {
        ClassRepository classRepository = new ClassRepository();
        Class clazz = new Class(createClassDTO.getCode());
        return classRepository.createClass(clazz);
    }
    public List<Student> getClassStudents(Class clazz) throws SQLException {
        ClassRepository classRepository = new ClassRepository();
        return classRepository.getClassStudents(clazz);
    }

    public void addStudentToClass(AddStudentToClassDTO dto)
            throws QueueJobException, StudentAlreadyInClassException, SQLException {
        ClassRepository classRepository = new ClassRepository();
        StudentRepository studentRepository = new StudentRepository();

        Class clazz = classRepository.getClassByCode(dto.getClassCode());
        Optional<Student> student = studentRepository.getStudentByCode(dto.getStudentCode());

        Student resolvedStudent = student.orElseThrow(() ->
                new RuntimeException("Student not found: " + dto.getStudentCode()));

        classRepository.addStudentToClass(clazz, resolvedStudent);
        logger.info("Class {} just added student {}", clazz.getCode(), resolvedStudent.getCode());
        SendAddedClassEmailTaskDTO sendAddedClassEmailTaskDTO = new SendAddedClassEmailTaskDTO(resolvedStudent.getCode());
        TaskManager.dispatch(new SendAddedClassEmailTask(), sendAddedClassEmailTaskDTO);
    }

    public void setTeacherToClass(Teacher teacher, Class clazz) throws SQLException {
        ClassRepository classRepository = new ClassRepository();
        classRepository.setTeacherToClass(clazz, teacher);
        logger.info("Class {} just updated teacher {}", clazz.getCode(), teacher.getName());
    }

    public void removeStudentFromClass(RemoveStudentFromClassDTO removeStudentFromClassDTO) throws SQLException {
        ClassRepository classRepository = new ClassRepository();
        classRepository.removeStudentFromClass(removeStudentFromClassDTO.getClazz(), removeStudentFromClassDTO.getStudent());
        logger.info("Removed student {} from class {}", removeStudentFromClassDTO.getStudent().getCode(), removeStudentFromClassDTO.getClazz().getCode());
    }

    private void sendAddStudentToClass(Student student) {
        logger.info("Sending email to student {}", student.getCode());
    }
}
