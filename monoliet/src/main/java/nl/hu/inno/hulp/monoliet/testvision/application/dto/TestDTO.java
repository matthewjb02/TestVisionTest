// In de TestDTO klasse
package nl.hu.inno.hulp.monoliet.testvision.application.dto;

import nl.hu.inno.hulp.monoliet.testvision.application.dto.GradingCriteriaDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.SubmissionDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.StatisticsDTO;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.test.Validation;

import java.util.List;

public class TestDTO {

    private Long id;
    private List<Question> questions;
    private String makerMail;
    private String testValidatorMail;
    private Validation validation;
    private String reason;
    private int totalPoints;
    private GradingCriteriaDTO gradingCriteria;
    private List<SubmissionDTO> submissions;
    private StatisticsDTO statistics;

    public TestDTO() {
    }

    public TestDTO(Long id, List<Question> questions, int totalPoints, String makerMail, String testValidatorMail, Validation validation, String reason, GradingCriteriaDTO gradingCriteria, List<SubmissionDTO> submissions, StatisticsDTO statistics) {
        this.id = id;
        this.questions = questions;
        this.totalPoints = totalPoints;
        this.makerMail = makerMail;
        this.testValidatorMail = testValidatorMail;
        this.validation = validation;
        this.reason = reason;
        this.gradingCriteria = gradingCriteria;
        this.submissions = submissions;
        this.statistics = statistics;
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

    public GradingCriteriaDTO getGradingCriteria() {
        return gradingCriteria;
    }

    public List<SubmissionDTO> getSubmissions() {
        return submissions;
    }

    public StatisticsDTO getStatistics() {
        return statistics;
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
