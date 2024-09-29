package nl.hu.inno.hulp.monoliet.testvision.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Student extends User {
    @Id
    @GeneratedValue
    private Long id;
    private boolean extraTimeRight = false;

    protected Student() {
        super();
    }

    public Student(String firstName, String lastName, boolean extraTimeRight) {
        super(firstName, lastName);
        this.extraTimeRight = extraTimeRight;
    }

    public Long getId() {
        return id;
    }

    public void changeExtraTimeRight(boolean right) {
        this.extraTimeRight = right;
    }

    public boolean isExtraTimeRight() {
        return extraTimeRight;
    }
}
