package nl.hu.inno.hulp.monoliet.testvision.presentation.dto;

import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;

public class StudentResponse {
    private final Long id;
    private final String voornaam;
    private final String achternaam;

    public StudentResponse(Student student) {
        this.id = student.getId();
        this.voornaam = student.getFirstName();
        this.achternaam = student.getLastName();
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
