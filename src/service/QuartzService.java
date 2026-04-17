package service;

import com.google.gson.Gson;
import dto.TaskDTO;
import exception.QueueJobException;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import task.QueueJob;
import task.quartz.GeneralQuartzJob;

public class QuartzService implements QueueJob {
    public static Scheduler scheduler;

    public void init() throws QueueJobException {
        try {
            scheduler = getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            throw new QueueJobException("Failed to initialize Quartz", e);
        }
    }

    public void shutdown() throws QueueJobException {
        try {
            scheduler = getScheduler();
            scheduler.shutdown();
        } catch (SchedulerException e) {
            throw new QueueJobException("Failed to shutdown Quartz", e);
        }
    }

    public static Scheduler getScheduler() throws SchedulerException {
        if (scheduler == null) {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();
        }
        return scheduler;
    }

    public void dispatchTask(String taskUUID, TaskDTO taskDTO) throws QueueJobException {
        try {
            Trigger trigger = TriggerBuilder.newTrigger()
                    .startNow()
                    .build();

            JobDetail job = JobBuilder.newJob(GeneralQuartzJob.class)
                    .withIdentity(taskUUID)
                    .usingJobData("dto", new Gson().toJson(taskDTO))
                    .build();

            Scheduler scheduler = QuartzService.getScheduler();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new QueueJobException("Failed to dispatch task", e);
        }
    }
}
