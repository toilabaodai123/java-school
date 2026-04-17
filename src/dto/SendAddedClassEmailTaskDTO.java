package dto;

import java.util.HashMap;

public class SendAddedClassEmailTaskDTO extends TaskDTO {
    private String studentCode;

    public SendAddedClassEmailTaskDTO(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentCode() {
        return studentCode;
    }
}
