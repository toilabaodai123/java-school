package task.quartz;

import com.google.gson.Gson;
import dto.SendAddedClassEmailTaskDTO;
import exception.SimpleJobExecutionException;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.ExecutableTask;

import java.sql.SQLException;
import java.util.HashMap;

import static task.TaskManager.*;

public class GeneralQuartzJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(GeneralQuartzJob.class);

    public void execute(JobExecutionContext jobExecutionContext) throws SimpleJobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        var dto = jobDataMap.getString("dto");
        SendAddedClassEmailTaskDTO dtoData = new Gson().fromJson(dto, SendAddedClassEmailTaskDTO.class);

        logger.info("Processing task {}", dtoData.getTaskUUID());

        ExecutableTask executableTask;

        try {
            String className = dtoData.getTaskMapAddress();

            HashMap<String, String> reverTaskMapping = getReversedTaskMapping();

            Class<? extends ExecutableTask> clazz = (Class<? extends ExecutableTask>) Class.forName(reverTaskMapping.get(className).toString());

            executableTask = clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new SimpleJobExecutionException("Failed to resolve ExecutableTask class:" + e.getMessage(),null);
        }

        try {
            executableTask.execute(dtoData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        logger.info("Successfully proceeded task {}", dtoData.getTaskUUID());
    }
}
