package task;

import dto.SendAddedClassEmailTaskDTO;

import java.sql.SQLException;

public interface ExecutableTask {
    void execute(SendAddedClassEmailTaskDTO sendAddedClassEmailTaskDTO) throws SQLException;
}
