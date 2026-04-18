package app;

import repository.ClassRepository;
import repository.StudentRepository;
import repository.TaskRepository;
import repository.TeacherRepository;
import service.*;

public class DependencyContainer {
    private final static StudentRepository studentRepository = new StudentRepository();
    private final static ClassRepository classRepository = new ClassRepository();
    private final static TaskRepository  taskRepository = new TaskRepository();
    private final static TeacherRepository teacherRepository = new TeacherRepository();

    private final static ClassService classService = new ClassService(classRepository,studentRepository);
//    public static EmailService emailService = new EmailService();
//    public static QuartzService quartzService = new QuartzService();
//    public static RabbitMQService  rabbitMQService = new RabbitMQService();
    private final static StudentService studentService = new StudentService(studentRepository);
    private final static TeacherService teacherService = new TeacherService(teacherRepository);

    public static StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public static ClassRepository getClassRepository() {
        return classRepository;
    }

    public static TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public static TeacherRepository getTeacherRepository() {
        return teacherRepository;
    }

    public static ClassService getClassService() {
        return classService;
    }

    public static StudentService getStudentService() {
        return studentService;
    }

    public static TeacherService getTeacherService() {
        return teacherService;
    }
}
