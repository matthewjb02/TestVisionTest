package nl.hu.inno.hulp.monoliet.testvision.domain.user;

public class User {
    private String voornaam;
    private String achternaam;

    protected User() {
    }

    public User(String voornaam, String achternaam) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
    }
}
