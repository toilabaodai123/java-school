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

    static {
        initTaskMapping();
    }

    public static void initQueueJobService() throws SchedulerException {
        logger.info("Initializing QueueJob Service...");
        HashMap<String, String> config = Main.getConfig();
        QueueJob queueJobService = getQueueJobService(config);
        queueJobService.init();
    }

    public static void shutdownQueueJobService() throws SchedulerException {
        logger.info("Shutting down QueueJob Service...");
        HashMap<String, String> config = Main.getConfig();
        QueueJob queueJobService = getQueueJobService(config);
        queueJobService.shutdown();
    }

    private static QueueJob getQueueJobService(HashMap<String, String> config) throws SchedulerException {
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

        HashMap<String, Task> taskQueue = TaskManager.getTaskQueue();

        var Task = new Task(taskUUID);
        data.put("task_map_address", taskMapAddress);
        data.put("task_id",taskUUID.toString());

        taskQueue.put(Task.getTaskId().toString(), Task);

        Trigger trigger = TriggerBuilder.newTrigger()
                .startNow()
                .build();

        JobDetail job = JobBuilder.newJob(GeneralQuartzJob.class)
                .withIdentity(taskUUID.toString())
                .usingJobData("dto", new Gson().toJson(taskDTO))
                .build();

        Scheduler scheduler = QuartzService.getScheduler();

        scheduler.scheduleJob(job, trigger);
    }

    private static void initTaskMapping() {
        taskMapping.put(SendAddedClassEmailTask.class.getName(), "send_added_email_to_student");
        reversedTaskMapping.put("send_added_email_to_student", SendAddedClassEmailTask.class.getName());
    }

    public static HashMap<String, String> getTaskMapping() {
        return taskMapping;
    }

    public static HashMap<String, String> getReversedTaskMapping() {
        return reversedTaskMapping;
    }

}
