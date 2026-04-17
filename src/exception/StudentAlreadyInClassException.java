package exception;

public class StudentAlreadyInClassException extends SimpleException{
    public StudentAlreadyInClassException(){
        super();
    }

    @Override
    public String getMessage(){
        return "Student already exists in class ";
    }
}
