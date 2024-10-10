// In de TestDTO klasse
package nl.hu.inno.hulp.commons.dto;

import nl.hu.inno.hulp.commons.enums.ValidationStatus;
import nl.hu.inno.hulp.commons.response.QuestionResponse;
import nl.hu.inno.hulp.commons.response.TeacherResponse;

import java.util.List;
public class ExamDTO {

    private Long id;
    private List<QuestionResponse> questions;
    private TeacherResponse examMaker;
    private TeacherResponse examValidator;
    private ValidationStatus validationStatus;
    private String reason;
    private int totalPoints;
    private GradingCriteriaDTO gradingCriteria;
    private List<SubmissionDTO> submissions;
    private StatisticsDTO statistics;

    public ExamDTO() {
    }

    public ExamDTO(Long id, List<QuestionResponse> questions, int totalPoints, TeacherResponse examMaker, TeacherResponse examValidator, ValidationStatus validationStatus, String reason, GradingCriteriaDTO gradingCriteria, List<SubmissionDTO> submissions, StatisticsDTO statistics) {
        this.id = id;
        this.questions = questions;
        this.totalPoints = totalPoints;
        this.examMaker = examMaker;
        this.examValidator = examValidator;
        this.validationStatus = validationStatus;
        this.reason = reason;
        this.gradingCriteria = gradingCriteria;
        this.submissions = submissions;
        this.statistics = statistics;
    }

    public Long getId() {
        return id;
    }

    public List<QuestionResponse> getQuestions() {
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
  
    public TeacherResponse getExamMaker() {
        return examMaker;
    }

    public TeacherResponse getExamValidator() {
        return examValidator;
    }

    public ValidationStatus getValidation() {
        return validationStatus;
    }

    public String getReason() {
        return reason;
    }

}
