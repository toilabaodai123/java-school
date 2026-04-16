package task.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static task.TaskManager.getTask;

public class SendAddedClassEmailJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(SendAddedClassEmailJob.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        var task = getTask(jobDataMap.getString("taskId"));
        logger.info("Processing task {}",task.getTaskId());
        try {
            task.getExecutableTask().executeTask(task.getTaskId().toString());
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        task.setIsFinished(true);
        logger.info("Successfully proceeded task {}",task.getTaskId());

    }
}
