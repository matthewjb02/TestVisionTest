package nl.hu.inno.hulp.monoliet.testvision.application;

import nl.hu.inno.hulp.monoliet.testvision.domain.Course;

import java.util.List;

public class TeacherDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Course> courses;

    public TeacherDTO() {

    }
    public TeacherDTO(long id,String firstName, String lastName, String email, List<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.courses = courses;
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
    public List<Course> getCourses() {
        return courses;
    }
    public long getId() {
        return id;
    }
}
