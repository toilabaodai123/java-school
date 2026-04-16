package task;

import org.quartz.SchedulerException;

public interface QueueJob {
    public void init() throws SchedulerException;
    public void shutdown() throws SchedulerException;
}
