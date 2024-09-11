package nl.hu.inno.hulp.monoliet.testvision.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Teacher extends User {
    @Id
    @GeneratedValue
    private Long id;

    protected Teacher() {
    }
    public Teacher(String voornaam, String achternaam) {
        super(voornaam, achternaam);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
