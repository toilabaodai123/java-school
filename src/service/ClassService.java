package service;

import dto.AddStudentToClassDTO;
import dto.CreateClassDTO;
import dto.RemoveStudentFromClassDTO;
import dto.SendAddedClassEmailTaskDTO;
import exception.ClassWithCodeDoesntExist;
import exception.QueueJobException;
import exception.StudentAlreadyInClassException;
import exception.StudentWithCodeDoesntExist;
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
import java.util.List;
import java.util.Optional;

public class ClassService {
    private static final Logger logger = LoggerFactory.getLogger(ClassService.class);
    private static int instanceCounter = 0;
    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;

    public ClassService(ClassRepository classRepository,  StudentRepository studentRepository) {
        ++instanceCounter;
        logger.info("Class Service Constructor  {}",instanceCounter);
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
    }

    public Class createClass(CreateClassDTO createClassDTO) throws SQLException {
        Class clazz = new Class(createClassDTO.getCode());
        return classRepository.createClass(clazz);
    }
    public List<Student> getClassStudents(Class clazz) throws SQLException {
        return classRepository.getClassStudents(clazz);
    }

    public void addStudentToClass(AddStudentToClassDTO dto)
            throws QueueJobException, StudentAlreadyInClassException, SQLException, ClassWithCodeDoesntExist, StudentWithCodeDoesntExist {
        Optional<Class> clazz = classRepository.getClassByCode(dto.getClassCode());
        if(clazz.isEmpty()){
            throw new ClassWithCodeDoesntExist(dto.getClassCode());
        }

        Optional<Student> student = studentRepository.getStudentByCode(dto.getStudentCode());

        Student resolvedStudent = student.orElseThrow(() ->
                new StudentWithCodeDoesntExist(dto.getStudentCode()));

        classRepository.addStudentToClass(clazz.get(), resolvedStudent);
        logger.info("Class {} just added student {}", clazz.get().getCode(), resolvedStudent.getCode());
        SendAddedClassEmailTaskDTO sendAddedClassEmailTaskDTO = new SendAddedClassEmailTaskDTO(resolvedStudent.getCode());
        TaskManager.dispatch(new SendAddedClassEmailTask(), sendAddedClassEmailTaskDTO);
    }

    public void setTeacherToClass(Teacher teacher, Class clazz) throws SQLException {
        classRepository.setTeacherToClass(clazz, teacher);
        logger.info("Class {} just updated teacher {}", clazz.getCode(), teacher.getName());
    }

    public void removeStudentFromClass(RemoveStudentFromClassDTO removeStudentFromClassDTO) throws SQLException {
        classRepository.removeStudentFromClass(removeStudentFromClassDTO.getClazz(), removeStudentFromClassDTO.getStudent());
        logger.info("Removed student {} from class {}", removeStudentFromClassDTO.getStudent().getCode(), removeStudentFromClassDTO.getClazz().getCode());
    }

}
