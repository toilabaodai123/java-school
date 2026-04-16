package service;

import model.Class;
import model.Student;
import model.Teacher;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.quartz.SendAddedClassEmailTask;
import task.TaskManager;

import java.util.HashMap;
import java.util.Optional;

public class ClassService {
    private static final Logger logger = LoggerFactory.getLogger(ClassService.class);

    public Optional<HashMap<String, Student>> getClassStudents(Class clazz) {
        return Optional.ofNullable(clazz.getStudents());
    }

    public void addStudentToClass(Student student, Class clazz) throws SchedulerException {
        if (clazz.getStudents().containsKey(student.getCode())) {
            logger.info("model.Student with code {} already exists in class {}", student.getCode(), clazz.getCode());
        } else {
            clazz.addStudent(student);
            logger.info("model.Class {} just added student {}", clazz.getCode(), student.getName());
            HashMap<String, String> data = new HashMap<>();
            data.put("student_code", student.getCode());
            TaskManager.dispatch(new SendAddedClassEmailTask(), data);
        }
    }

    public void setTeacherToClass(Teacher teacher, Class clazz) {
        clazz.setTeacher(teacher);
        logger.info("model.Class {} just updated teacher {}", clazz.getCode(), teacher.getName());
    }

    public void removeStudentFromClass(Student student, Class clazz) {
        HashMap<String, Student> students = clazz.getStudents();

        if (!students.containsKey(student.getCode())) {
            logger.error("model.Class {} does not have student code {}", clazz.getCode(), student.getCode());
        } else {
            students.remove(student.getCode());
        }
    }

    private void sendAddStudentToClass(Student student) {
        logger.info("Sending email to student {}", student.getCode());
    }
}
