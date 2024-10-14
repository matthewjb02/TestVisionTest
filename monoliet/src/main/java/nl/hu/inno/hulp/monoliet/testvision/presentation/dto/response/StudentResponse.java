package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;

public class StudentResponse {
    private final Long id;
    private final String voornaam;
    private final String achternaam;
    private final String email;

    public StudentResponse(Student student) {
        this.id = student.getId();
        this.voornaam = student.getFirstName();
        this.achternaam = student.getLastName();
        this.email = student.getEmail().getEmailString();
    }

    public Long getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }
}
