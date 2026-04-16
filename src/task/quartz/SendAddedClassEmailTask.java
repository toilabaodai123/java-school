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

    @Override
    public void executeTask(String taskId) throws SchedulerException {
        logger.info("Handled task {}",taskId);
    }
}
