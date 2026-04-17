package task;

import dto.TaskDTO;
import org.quartz.SchedulerException;

public interface QueueJob {
    public void init() throws SchedulerException;
    public void shutdown() throws SchedulerException;
    public void dispatchTask(String taskUUID, TaskDTO taskDTO) throws SchedulerException;
}
