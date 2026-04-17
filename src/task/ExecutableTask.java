package task;

import dto.SendAddedClassEmailTaskDTO;

public interface ExecutableTask {
    void execute(SendAddedClassEmailTaskDTO sendAddedClassEmailTaskDTO);
}
