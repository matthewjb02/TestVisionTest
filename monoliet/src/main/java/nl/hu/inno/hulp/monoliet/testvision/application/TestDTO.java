package nl.hu.inno.hulp.monoliet.testvision.application;

import nl.hu.inno.hulp.monoliet.testvision.domain.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.Validation;

import java.util.List;

public class TestDTO {

    private Long id;
    private String makerMail;
    private String testValidatorMail;
    private List<Question> questions;
    private Validation validation;
    private String reason;
    private int totalPoints;

    public TestDTO(){

    }

    public TestDTO(Long id, List<Question> questions, int totalPoints, String makerMail, String testValidatorMail, Validation validation, String reason) {
        this.id = id;
        this.questions = questions;
        this.totalPoints = totalPoints;
        this.makerMail = makerMail;
        this.testValidatorMail = testValidatorMail;
        this.validation = validation;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public String getMakerMail() {
        return makerMail;
    }

    public String getTestValidatorMail() {
        return testValidatorMail;
    }

    public Validation getValidation() {
        return validation;
    }

    public String getReason() {
        return reason;
    }
}
