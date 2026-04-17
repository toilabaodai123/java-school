package app;

import exception.StudentAlreadyInClassException;
import model.Class;
import model.Student;
import model.Teacher;
import exception.QueueJobException;
import exception.StudentAlreadyInClassException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClassService;
import task.TaskManager;

import java.util.HashMap;
import java.util.Optional;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static HashMap<String, String> config;
    public static void main(String[] args) throws InterruptedException, QueueJobException, StudentAlreadyInClassException {
        logger.info("Starting...");

        Main.start();
            var student = new Student("name-1","code-1");
            var student2 = new Student("name-2","code-2");
            var student3 = new Student("name-3","code-3");
            var teacher = new Teacher();
            var class1 = new Class("1");
            var classService = new ClassService();
        try{
            classService.addStudentToClass(student,class1);
            classService.addStudentToClass(student,class1);
            classService.addStudentToClass(student2,class1);
            classService.setTeacherToClass(teacher,class1);
            Optional<HashMap<String, Student>> class1Students = classService.getClassStudents(class1);
            logger.info("model.Class {} has {} students",class1.getCode(), class1Students.map(h -> h.size()).orElse(0));
            classService.removeStudentFromClass(student,class1);
            classService.removeStudentFromClass(student3,class1);
            logger.info("model.Class {} has {} students",class1.getCode(), class1Students.map(h -> h.size()).orElse(0));

        }catch(StudentAlreadyInClassException e){
            logger.error("Student with code {} already exists in class {}, message error: {}", student.getCode(), class1.getCode(),e.getMessage());
        }


    }

    private static void start() throws QueueJobException {
        config = initConfig();
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
//        config.put("task.queue","internal");
        config.put("task.queue","rabbitmq");
        return config;
    }

    public static HashMap<String, String> getConfig() {
        return config;
    }

}
