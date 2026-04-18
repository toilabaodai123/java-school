package task.quartz;

import app.DependencyContainer;
import app.Main;
import com.google.gson.Gson;
import dto.SendAddedClassEmailTaskDTO;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.EmailService;
import task.ExecutableTask;

import java.sql.SQLException;
import java.util.HashMap;

public class SendAddedClassEmailTask implements ExecutableTask {

    private static final Logger logger = LoggerFactory.getLogger(SendAddedClassEmailTask.class);

    @Override
    public void execute(SendAddedClassEmailTaskDTO dto) throws SQLException {
        var studentService = DependencyContainer.getStudentService();
        var student = studentService.getStudentByCode(dto.getStudentCode());
        logger.info("Handling task {}, request info: {}", dto.getTaskUUID(), new Gson().toJson(dto));

        if (student.isEmpty()) {
            logger.warn("Student with code {} not found, skipping email", dto.getStudentCode());
            return;
        }

        HashMap<String, String> config = Main.getConfig();
        EmailService emailService = new EmailService(
                config.get("mail.host"),
                Integer.parseInt(config.get("mail.port"))
        );

        try {
            emailService.sendEmail(
                    "school@example.com",
                    student.get().getEmail(),
                    "You've been added to a class",
                    "Hello " + dto.getStudentCode() + ",\n\nYou have been added to a new class.\n\nRegards,\nSchool Admin"
            );
        } catch (MessagingException e) {
            logger.error("Failed to send email for task {}: {}", dto.getTaskUUID(), e.getMessage());
        }
    }
}
