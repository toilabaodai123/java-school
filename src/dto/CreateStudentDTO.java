package dto;

public class CreateStudentDTO extends CreateTeacherDTO{
    private final String email;

    public CreateStudentDTO(String code, String name, String email) {
        super(code, name);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
