package service;

import dto.SendAddedClassEmailTaskDTO;
import exception.QueueJobException;
import exception.StudentAlreadyInClassException;
import model.Class;
import model.Student;
import model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.quartz.SendAddedClassEmailTask;
import task.TaskManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ClassService {
    private List<Class> classList = new ArrayList<Class>();
    private static final Logger logger = LoggerFactory.getLogger(ClassService.class);

    public Optional<HashMap<String, Student>> getClassStudents(Class clazz) {
        return Optional.ofNullable(clazz.getStudents());
    }

    public void addStudentToClass(Student student, Class clazz) throws QueueJobException, StudentAlreadyInClassException {
        if (clazz.getStudents().containsKey(student.getCode())) {
            logger.info("Student with code {} already exists in class {}", student.getCode(), clazz.getCode());
//            throw new StudentAlreadyInClassException();
        } else {
            clazz.addStudent(student);
            logger.info("Class {} just added student {}", clazz.getCode(), student.getName());
            SendAddedClassEmailTaskDTO sendAddedClassEmailTaskDTO = new SendAddedClassEmailTaskDTO(student.getCode());
            TaskManager.dispatch(new SendAddedClassEmailTask(), sendAddedClassEmailTaskDTO);
        }
    }

    public void setTeacherToClass(Teacher teacher, Class clazz) {
        clazz.setTeacher(teacher);
        logger.info("Class {} just updated teacher {}", clazz.getCode(), teacher.getName());
    }

    public void removeStudentFromClass(Student student, Class clazz) {
        HashMap<String, Student> students = clazz.getStudents();

        if (!students.containsKey(student.getCode())) {
            logger.error("Class {} does not have student code {}", clazz.getCode(), student.getCode());
        } else {
            students.remove(student.getCode());
        }
    }

    private void sendAddStudentToClass(Student student) {
        logger.info("Sending email to student {}", student.getCode());
    }
}
