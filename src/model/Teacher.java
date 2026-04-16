package model;

public class Teacher extends Person {

    public Teacher(){
        this.name = "default-teacher-name";
        this.code = "default-teacher-code";
    }

    public Teacher(String name,String code){
        this.name = name;
        this.code = code;
    }

    @Override
    public String work(){
        return "Working...";
    }

    @Override
    public String toString() {
        return """
                {
                    "name": %s,
                    "code": %s,
                }
                """.formatted(this.name,this.code);
    }
}
