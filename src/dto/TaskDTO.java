package dto;

import java.util.HashMap;

public class TaskDTO {
    private String taskUUID;
    private String taskMapAddress;
    private String metadata;

    public String getTaskUUID() {
        return taskUUID;
    }
    public String getTaskMapAddress() {
        return taskMapAddress;
    }
    public String getMetadata() {
        return metadata;
    }

    public void setTaskUUID(String taskUUID) {
        this.taskUUID = taskUUID;
    }

    public void setTaskMapAddress(String taskMapAddress) {
        this.taskMapAddress = taskMapAddress;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
