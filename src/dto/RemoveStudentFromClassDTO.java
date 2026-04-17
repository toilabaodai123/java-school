package dto;

import model.Student;
import model.Class;

public class RemoveStudentFromClassDTO {
    private final Student student;
    private final Class clazz;
    public RemoveStudentFromClassDTO(Student student, Class clazz) {
        this.student = student;
        this.clazz = clazz;
    }
    public Student getStudent() {
        return student;
    }
    public Class getClazz() {
        return clazz;
    }
}
