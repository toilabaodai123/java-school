package task;

import dto.TaskDTO;

public class Task {
    private String taskId;
    private TaskDTO body;
    private int queueType;
    private Boolean isFinished;

    public Task(String taskId) {
        this.taskId = taskId;
        this.queueType = 1;
        this.isFinished = false;
    }

    public void setBody(TaskDTO body) {
        this.body = body;
    }

    public void setQueueType(int queueType) {
        this.queueType = queueType;
    }
}
