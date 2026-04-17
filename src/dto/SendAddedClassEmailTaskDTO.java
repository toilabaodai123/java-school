package dto;

import java.util.HashMap;

public class SendAddedClassEmailTaskDTO extends TaskDTO {
    private final String studentCode;

    public SendAddedClassEmailTaskDTO(String studentCode) {
        this.studentCode = studentCode;
    }
}
