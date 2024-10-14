package nl.hu.inno.hulp.commons.messaging;


import lombok.Data;
import lombok.Getter;

@Data
public class TeacherDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public TeacherDTO(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}