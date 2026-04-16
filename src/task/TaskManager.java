package task;

import app.Main;
import com.google.gson.Gson;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.QuartzService;
import task.quartz.GeneralQuartzJob;
import task.quartz.SendAddedClassEmailJob;

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

    public static int getTaskQueueSize() {
        return taskQueue.size();
    }

    public static void initQueueJobService() throws SchedulerException {
        HashMap<String, String> config = Main.getConfig();
        QueueJob queueJobService = getQueueJobService(config);
        queueJobService.init();
    }

    public static void shutdownQueueJobService() throws SchedulerException {
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

    public static void dispatch(ExecutableTask executableTask, HashMap<String, String> data) throws SchedulerException {
        UUID taskUUID = UUID.randomUUID();

        HashMap<String, Task> taskQueue = TaskManager.getTaskQueue();

        var Task = new Task(taskUUID);
        Task.setExecutableTask(executableTask);

        taskQueue.put(Task.getTaskId().toString(), Task);

        Trigger trigger = TriggerBuilder.newTrigger()
                .startNow()
                .build();

        JobDetail job = JobBuilder.newJob(GeneralQuartzJob.class)
                .withIdentity(taskUUID.toString())
                .usingJobData("taskId", taskUUID.toString())
                .usingJobData("body", new Gson().toJson(data))
                .build();

        Scheduler scheduler = QuartzService.getScheduler();

        scheduler.scheduleJob(job, trigger);
    }

}
