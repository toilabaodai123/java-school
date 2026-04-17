package task;

import dto.TaskDTO;
import exception.QueueJobException;

public interface QueueJob {
    public void init() throws QueueJobException;
    public void shutdown() throws QueueJobException;
    public void dispatchTask(String taskUUID, TaskDTO taskDTO) throws QueueJobException;
}
