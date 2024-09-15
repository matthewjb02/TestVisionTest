package nl.hu.inno.hulp.monoliet.testvision.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Student extends User {
    @Id
    @GeneratedValue
    private Long id;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    protected Student() {
        super();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
