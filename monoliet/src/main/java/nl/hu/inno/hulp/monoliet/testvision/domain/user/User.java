package nl.hu.inno.hulp.monoliet.testvision.domain.user;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class User {
    private String voornaam;
    private String achternaam;

    protected User() {
    }

    public User(String voornaam, String achternaam) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }
}
