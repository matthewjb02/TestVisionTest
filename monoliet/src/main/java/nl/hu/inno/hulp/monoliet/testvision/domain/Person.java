package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Person {
    private  String firstName;
    private  String lastName;
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person() {

    }


    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
