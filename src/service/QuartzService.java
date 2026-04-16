package service;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import task.QueueJob;

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
}
