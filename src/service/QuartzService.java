package service;

import com.google.gson.Gson;
import dto.TaskDTO;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import task.QueueJob;
import task.quartz.GeneralQuartzJob;

public class QuartzService implements QueueJob {
    public static Scheduler scheduler;

    public void init() throws SchedulerException {
        scheduler = getScheduler();
        scheduler.start();
    }

    public void shutdown() throws SchedulerException {
        scheduler = getScheduler();

        scheduler.shutdown();
    }

    public static Scheduler getScheduler() throws SchedulerException {
        if (scheduler == null) {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();
        }
        return scheduler;
    }

    public void dispatchTask(String taskUUID,TaskDTO taskDTO) throws SchedulerException {
        Trigger trigger = TriggerBuilder.newTrigger()
                .startNow()
                .build();

        JobDetail job = JobBuilder.newJob(GeneralQuartzJob.class)
                .withIdentity(taskUUID)
                .usingJobData("dto", new Gson().toJson(taskDTO))
                .build();

        Scheduler scheduler = QuartzService.getScheduler();

        scheduler.scheduleJob(job, trigger);
    }
}
