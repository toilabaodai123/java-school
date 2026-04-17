package task;

import app.Main;
import com.google.gson.Gson;
import dto.TaskDTO;
import exception.QueueJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.QuartzService;
import service.RabbitMQService;
import task.quartz.GeneralQuartzJob;
import task.quartz.SendAddedClassEmailTask;

import java.util.HashMap;
import java.util.List;
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
        } catch (QueueJobException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initQueueJobService() throws QueueJobException {
        logger.info("Initializing QueueJob Service...");
        queueJob.init();
    }

    public static void shutdownQueueJobService() throws QueueJobException {
        logger.info("Shutting down QueueJob Service...");
        queueJob.shutdown();
    }

    private static QueueJob getQueueJobService(HashMap<String, String> config) {
        if (queueJob != null) {
            return queueJob;
        }

        String queue = "internal";

        if (config.containsKey("task.queue.driver")) {
            queue = config.get("task.queue.driver");
        }

        QueueJob queueJob = null;

        switch (queue) {
            case "internal":
                queueJob = new QuartzService();
                break;
            case "rabbitmq":
                queueJob = new RabbitMQService();
                break;
            default:
                throw new IllegalArgumentException("Unknown queue type");
        }

        return queueJob;
    }

    public static void dispatch(ExecutableTask executableTask, TaskDTO taskDTO) throws QueueJobException {
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

    private static void initQueueJob() throws QueueJobException {
        HashMap<String, String> config = Main.getConfig();
        queueJob = getQueueJobService(config);
    }

    public static HashMap<String, String> getTaskMapping() {
        return taskMapping;
    }

    public static HashMap<String, String> getReversedTaskMapping() {
        return reversedTaskMapping;
    }

    public static List<String> getNonCriticalTasks() {
        return List.of("send_added_email_to_student");
    }

}
