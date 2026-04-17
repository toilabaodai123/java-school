package task;

import app.Main;
import com.google.gson.Gson;
import dto.TaskDTO;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.QuartzService;
import task.quartz.GeneralQuartzJob;
import task.quartz.SendAddedClassEmailTask;

import java.util.HashMap;
import java.util.UUID;

public class TaskManager {
    private static final Logger logger = LoggerFactory.getLogger(TaskManager.class);

    private static HashMap<String, Task> taskQueue = new HashMap<>();

    public static HashMap<String, Task> getTaskQueue() {
        return taskQueue;
    }

    public static Task getTask(String uuid) {
        return taskQueue.get(uuid);
    }

    public static HashMap<String, String> taskMapping = new HashMap<>();

    public static HashMap<String, String> reversedTaskMapping = new HashMap<>();

    private static QueueJob queueJob = null;

    static {
        initTaskMapping();
        try {
            initQueueJob();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initQueueJobService() throws SchedulerException {
        logger.info("Initializing QueueJob Service...");
        queueJob.init();
    }

    public static void shutdownQueueJobService() throws SchedulerException {
        logger.info("Shutting down QueueJob Service...");
        queueJob.shutdown();
    }

    private static QueueJob getQueueJobService(HashMap<String, String> config) throws SchedulerException {
        if (queueJob != null) {
            return queueJob;
        }

        String queue = "internal";

        if (config.containsKey("task.queue")) {
            queue = config.get("task.queue");
        }

        QueueJob queueJob = null;

        switch (queue) {
            case "internal":
                queueJob = new QuartzService();
                break;
            case "external":
                break;
            default:
                throw new IllegalArgumentException("Unknown queue type");
        }

        return queueJob;
    }

    public static void dispatch(ExecutableTask executableTask, TaskDTO taskDTO) throws SchedulerException {
        UUID taskUUID = UUID.randomUUID();
        var taskMap = getTaskMapping();
        String taskMapAddress = taskMap.get(executableTask.getClass().getName());

        taskDTO.setTaskMapAddress(taskMapAddress);
        taskDTO.setTaskUUID(taskUUID.toString());

        queueJob.dispatchTask(taskUUID.toString(), taskDTO);
    }

    private static void initTaskMapping() {
        taskMapping.put(SendAddedClassEmailTask.class.getName(), "send_added_email_to_student");
        reversedTaskMapping.put("send_added_email_to_student", SendAddedClassEmailTask.class.getName());
    }

    private static void initQueueJob() throws SchedulerException {
        HashMap<String, String> config = Main.getConfig();
        queueJob = getQueueJobService(config);
    }

    public static HashMap<String, String> getTaskMapping() {
        return taskMapping;
    }

    public static HashMap<String, String> getReversedTaskMapping() {
        return reversedTaskMapping;
    }

}
