package exception;

public class ClassWithCodeDoesntExist extends SimpleException{
    private final String code;

    public ClassWithCodeDoesntExist(String code){
        this.code = code;
    }

    @Override
    public String getMessage() {
        return "Class with code " + code + " does not exist";
    }
}
