package task.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static task.TaskManager.getTask;

public class SendAddedClassEmailJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(SendAddedClassEmailTask.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        var task = getTask(jobDataMap.getString("taskId"));
        logger.info("Processing task {}",task.getTaskId());

        task.setIsFinished(true);
        logger.info("Successfully proceeded task {}",task.getTaskId());

    }
}
