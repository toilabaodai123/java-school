package task;

import dto.SendAddedClassEmailTaskDTO;
import dto.TaskDTO;
import org.quartz.SchedulerException;

public interface ExecutableTask {
    void execute(SendAddedClassEmailTaskDTO sendAddedClassEmailTaskDTO) throws SchedulerException;
}
