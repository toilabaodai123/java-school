package task.quartz;

import com.google.gson.Gson;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.ExecutableTask;

import java.util.HashMap;

import static task.TaskManager.getTask;

public class GeneralQuartzJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(GeneralQuartzJob.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        var task = getTask(jobDataMap.getString("taskId"));
        var data = jobDataMap.getString("data");
        HashMap<String, String> realData = new Gson().fromJson(data, HashMap.class);
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
