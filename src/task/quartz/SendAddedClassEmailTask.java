package task.quartz;

import dto.SendAddedClassEmailTaskDTO;
import dto.TaskDTO;
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
    public void execute(SendAddedClassEmailTaskDTO sendAddedClassEmailTaskDTO) throws SchedulerException {
        logger.info("Handled task {}",taskDTO.getTaskUUID().toString());
    }
}
