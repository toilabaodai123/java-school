package task;

import java.util.HashMap;
import java.util.UUID;

public class Task {
    private UUID taskId;
    private Boolean isFinished;

    public Task(UUID taskId) {
        this.taskId = taskId;
        this.isFinished = false;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }
}
