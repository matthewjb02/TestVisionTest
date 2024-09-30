package nl.hu.inno.hulp.monoliet.testvision.domain.user;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class User {
    String firstName;
    String lastName;

    protected User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
