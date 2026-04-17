package model;

public class Student extends Person {
    private String email;
    public Student(){
        this.name = "default-student-name";
        this.code = "default-student-code";
        this.email = "default-student-email";
    }

    public Student(String name, String code, String email) {
        this.name = name;
        this.code = code;
        this.email = email;
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

    public void setEmail(String email) {
        this.email = email;
    }
}
