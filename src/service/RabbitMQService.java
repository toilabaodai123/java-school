package service;

import dto.TaskDTO;
import org.quartz.SchedulerException;
import task.QueueJob;

public class RabbitMQService implements QueueJob {
    public void init() throws SchedulerException {

    };
    public void shutdown() throws SchedulerException {

    };
    public void dispatchTask(String taskUUID, TaskDTO taskDTO) throws SchedulerException {
        
    };
}
