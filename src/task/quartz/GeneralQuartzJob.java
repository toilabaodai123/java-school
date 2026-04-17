package task.quartz;

import com.google.gson.Gson;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.ExecutableTask;

import java.util.HashMap;

import static task.TaskManager.*;

public class GeneralQuartzJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(GeneralQuartzJob.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        var task = getTask(jobDataMap.getString("taskId"));
        var dto = jobDataMap.getString("dto");
        HashMap dtoData = new Gson().fromJson(dto, HashMap.class);
        logger.info("Processing task {}", task.getTaskId());

        ExecutableTask executableTask;

        try {
            String className = dtoData.get("task_map_address").toString();

            HashMap<String, String> reverTaskMapping = getReversedTaskMapping();

            Class<? extends ExecutableTask> clazz = (Class<? extends ExecutableTask>) Class.forName(reverTaskMapping.get(className).toString());

            executableTask = clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new JobExecutionException("Failed to resolve ExecutableTask class", e);
        } catch (ReflectiveOperationException e) {
            throw new JobExecutionException("Failed to instantiate ExecutableTask", e);
        }

        try {
            executableTask.executeTask();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        task.setIsFinished(true);

        logger.info("Successfully proceeded task {}", task.getTaskId());
    }
}
