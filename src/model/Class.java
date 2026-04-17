package model;

import java.util.HashMap;
import java.util.Optional;

public class Class {
    private long id;
    private String code;
    private HashMap<String, Student> students;
    private Optional<Teacher> teacher = Optional.empty();

    public Class() {
        this.code = "default-code";
        this.students = new HashMap<>();
        this.teacher = Optional.empty();
    }

    public Class(String code) {
        this.code = code;
        students = new HashMap<>();
    }

    public Optional<Teacher> getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = Optional.ofNullable(teacher);
    }

    public void addStudent(Student student) {
        this.students.put(student.getCode(), student);
    }

    public HashMap<String, Student> getStudents() {
        return students;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
