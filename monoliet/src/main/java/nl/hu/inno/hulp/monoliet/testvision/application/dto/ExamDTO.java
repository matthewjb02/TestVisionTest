// In de TestDTO klasse
package nl.hu.inno.hulp.monoliet.testvision.application.dto;

import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Validation;

import java.util.List;

public class ExamDTO {

    private Long id;
    private List<QuestionDTO> questions;
    private String makerMail;
    private String examValidatorMail;
    private Validation validation;
    private String reason;
    private int totalPoints;
    private GradingCriteriaDTO gradingCriteria;
    private List<SubmissionDTO> submissions;
    private StatisticsDTO statistics;

    public ExamDTO() {
    }

    public ExamDTO(Long id, List<QuestionDTO> questions, int totalPoints, String makerMail, String examValidatorMail, Validation validation, String reason, GradingCriteriaDTO gradingCriteria, List<SubmissionDTO> submissions, StatisticsDTO statistics) {
        this.id = id;
        this.questions = questions;
        this.totalPoints = totalPoints;
        this.makerMail = makerMail;
        this.examValidatorMail = examValidatorMail;
        this.validation = validation;
        this.reason = reason;
        this.gradingCriteria = gradingCriteria;
        this.submissions = submissions;
        this.statistics = statistics;
    }

    public Long getId() {
        return id;
    }

    public List<QuestionDTO> getQuestions() {
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

    public String getExamValidatorMail() {
        return examValidatorMail;
    }

    public Validation getValidation() {
        return validation;
    }

    public String getReason() {
        return reason;
    }
}
