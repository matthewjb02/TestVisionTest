package nl.hu.inno.hulp.monoliet.testvision.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.ValidationStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Teacher extends User {
    @GeneratedValue
    @Id
    private long id;
    @Embedded
    private TeacherEmail email;
    public Teacher(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new TeacherEmail(email);
    }
    protected Teacher (){
        super();
    }
    public TeacherEmail getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }
}
