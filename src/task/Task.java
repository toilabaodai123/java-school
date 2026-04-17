package task;

import dto.TaskDTO;

import java.util.HashMap;
import java.util.UUID;

public class Task {
    private String taskId;
    private HashMap<String, String> metadata;
    private TaskDTO body;
    private int queueType;
    private Boolean isFinished;

    public Task(String taskId) {
        this.taskId = taskId;
        this.queueType = 1;
        this.isFinished = false;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(HashMap<String, String> metadata) {
        this.metadata = metadata;
    }

    public TaskDTO getBody() {
        return body;
    }

    public void setBody(TaskDTO body) {
        this.body = body;
    }

    public int getQueueType() {
        return queueType;
    }

    public void setQueueType(int queueType) {
        this.queueType = queueType;
    }
}
