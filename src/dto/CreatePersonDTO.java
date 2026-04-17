package dto;

public class CreatePersonDTO {
    private final String code;
    private final String name;

    public CreatePersonDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
