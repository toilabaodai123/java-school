package task;

import org.quartz.SchedulerException;

public interface ExecutableTask {
    void executeTask(String taskId) throws SchedulerException;
}
