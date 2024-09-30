package nl.hu.inno.hulp.monoliet.testvision.application.dto;

import nl.hu.inno.hulp.monoliet.testvision.domain.Course;

import java.util.List;

public class TeacherDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    public TeacherDTO() {

    }
    public TeacherDTO(long id,String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }
}
