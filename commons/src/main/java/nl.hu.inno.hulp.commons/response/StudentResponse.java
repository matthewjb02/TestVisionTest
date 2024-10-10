package nl.hu.inno.hulp.commons.response;

public class StudentResponse {
    private final Long id;
    private final String voornaam;
    private final String achternaam;
    private final String email;

    public StudentResponse(Long id, String voornaam, String achternaam, String email) {
        this.id = id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }
}
