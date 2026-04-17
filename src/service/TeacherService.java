package service;

import model.Student;
import model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TeacherService {
    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    private List<Teacher> teacherList = new ArrayList<Teacher>();

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public Teacher addTeacher(Teacher teacher){
        teacherList.add(teacher);
        return teacher;
    }
}
