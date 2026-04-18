package exception;

public class StudentWithCodeDoesntExist extends SimpleException{
    private final String code;

    public StudentWithCodeDoesntExist(String code){
        this.code = code;
    }

    @Override
    public String getMessage() {
        return "Student with code " + code + " does not exist";
    }
}
