package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
@Getter
public class TeacherResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    protected TeacherResponse() {

    }
    public TeacherResponse(Teacher teacher) {
        this.id = teacher.getId();
        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
        this.email = teacher.getEmail().getEmailString();
    }

}
