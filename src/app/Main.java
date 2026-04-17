package app;

import database.DatabaseInitializer;
import dto.AddStudentToClassDTO;
import dto.CreateClassDTO;
import dto.CreateStudentDTO;
import dto.RemoveStudentFromClassDTO;
import exception.StudentAlreadyInClassException;
import model.Class;
import model.Student;
import model.Teacher;
import exception.QueueJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClassService;
import service.StudentService;
import service.TeacherService;
import task.TaskManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static HashMap<String, String> config;
    public static void main(String[] args) throws InterruptedException, QueueJobException, StudentAlreadyInClassException, SQLException {
        logger.info("Starting...");

        Main.start();
        var studentService = new StudentService();
        var teacherService = new TeacherService();
        var classService = new ClassService();

        var createStudentDTO_1 = new CreateStudentDTO("code-1","name-1","email-1");
        var createStudentDTO_2 = new CreateStudentDTO("code-2","name-2","email-2");
        var createStudentDTO_3 = new CreateStudentDTO("code-3","name-3","email-3");

        Student student_1 = studentService.createStudent(createStudentDTO_1);
        Student student_2 = studentService.createStudent(createStudentDTO_2);
        Student student_3 = studentService.createStudent(createStudentDTO_3);
        Teacher teacher = teacherService.addTeacher(new Teacher());

        var createClassDTO = new CreateClassDTO("code-1");
        Class clazz = classService.createClass(createClassDTO);


        try{
            classService.addStudentToClass(new AddStudentToClassDTO(student_1.getCode(), clazz.getCode()));
            classService.addStudentToClass(new AddStudentToClassDTO(student_2.getCode(), clazz.getCode()));
            classService.addStudentToClass(new AddStudentToClassDTO(student_3.getCode(), clazz.getCode()));
            classService.setTeacherToClass(teacher,clazz);
            List<Student> class1Students = classService.getClassStudents(clazz);
            logger.info("model.Class {} has {} students",clazz.getCode(), class1Students.size());
            var removeStudent1FromClass = new RemoveStudentFromClassDTO(student_1,clazz);
            var removeStudent2FromClass = new RemoveStudentFromClassDTO(student_2,clazz);
            classService.removeStudentFromClass(removeStudent1FromClass);
            classService.removeStudentFromClass(removeStudent2FromClass);
            class1Students = classService.getClassStudents(clazz);
            logger.info("model.Class {} has {} students",clazz.getCode(), class1Students.size());

        }catch(StudentAlreadyInClassException e){
//            logger.error("Student with code {} already exists in class {}, message error: {}", student.getCode(), class1.getCode(),e.getMessage());
        }


    }

    private static void start() throws QueueJobException, SQLException {
        config = initConfig();
        DatabaseInitializer.initialize();
        TaskManager.initQueueJobService();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down...");
            try {
                Main.shutdown();
            } catch (QueueJobException e) {
                throw new RuntimeException(e);
            }
        }));

        logger.info("Starting completed!...");
    }
    private static void shutdown() throws QueueJobException {
        TaskManager.shutdownQueueJobService();

        logger.info("Shutting down completed!...");
    }

    private static HashMap<String, String> initConfig(){
        var config = new HashMap<String, String>();
        config.put("task.queue","internal");
//        config.put("task.queue.driver","rabbitmq");
        config.put("task.queue.host","localhost");
        config.put("mail.host", "localhost");
        config.put("mail.port", "1025");
        config.put("db.url", "jdbc:mysql://localhost:3306/school");
        config.put("db.user", "school");
        config.put("db.password", "school");

        return config;
    }

    public static HashMap<String, String> getConfig() {
        return config;
    }
}
