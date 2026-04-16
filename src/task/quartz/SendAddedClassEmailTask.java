package task.quartz;

import model.Student;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.QuartzService;
import task.ExecutableTask;
import task.Task;
import task.TaskManager;

import java.util.HashMap;
import java.util.UUID;

public class SendAddedClassEmailTask implements ExecutableTask {

    private static final Logger logger = LoggerFactory.getLogger(SendAddedClassEmailTask.class);
    private final Student student;

    public SendAddedClassEmailTask(Student student) {
        this.student = student;
    }

    @Override
    public void executeTask(String taskId) throws SchedulerException {
        String body = "{"}"
        Trigger trigger = TriggerBuilder.newTrigger()
                .startNow() // Run exactly at this date/time
                .build();

        JobDetail job = JobBuilder.newJob(SendAddedClassEmailJob.class)
                .withIdentity(taskId)
                .usingJobData("taskId", taskId)
                .usingJobData("studentCode", student.getCode())
                .build();

        Scheduler scheduler = QuartzService.getScheduler();

        scheduler.scheduleJob(job, trigger);

        logger.info("Dispatching task {}",taskId);
    }
}
