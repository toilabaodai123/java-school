package model;

public class Student extends Person {
    public Student(){
        this.name = "default-student-name";
        this.code = "default-student-code";
    }

    public Student(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return """
                {
                    "name": %s,
                    "code": %s
                }
                """.formatted(this.name, this.code);
    }

    @Override
    public String work(){
        return "Learning...";
    }
}
