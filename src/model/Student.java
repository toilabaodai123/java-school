package model;

public class Student extends Person {
    private String email;
    public Student(){
        this.name = "default-student-name";
        this.code = "default-student-code";
        this.email = "default-student-email";
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

    public String getEmail() {
        return email;
    }
}
