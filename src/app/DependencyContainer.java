package app;

import repository.ClassRepository;
import repository.StudentRepository;
import repository.TaskRepository;
import repository.TeacherRepository;
import service.*;

import java.util.Optional;

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

    public static ClassService getClassService() {
        printCaller("getClassService");
        return classService;
    }

    public static StudentService getStudentService() {
        printCaller("getStudentService");
        return studentService;
    }

    public static TeacherService getTeacherService() {
        printCaller("getClassService");
        return teacherService;
    }

    private static void printCaller(String message) {
        StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

        // Use walk to process the stream of stack frames
        Optional<String> callerClassName = walker.walk(frames ->
                frames.skip(2) // Skip the current method (printCaller)
                        .findFirst()
                        .map(StackWalker.StackFrame::getClassName)
        );

        System.out.println(message + " Called by: " + callerClassName.orElse("Unknown"));
    }
}
