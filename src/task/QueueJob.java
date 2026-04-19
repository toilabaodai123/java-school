package task;

import dto.TaskDTO;
import exception.QueueJobException;

public interface QueueJob {
    void init() throws QueueJobException;
    void shutdown() throws QueueJobException;
    void dispatchTask(String taskUUID, TaskDTO taskDTO) throws QueueJobException;

    default void helloWorld(){
        System.out.println("Hello World!");
    }
}
