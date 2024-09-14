// In de TestDTO klasse
package nl.hu.inno.hulp.monoliet.testvision.application;

import nl.hu.inno.hulp.monoliet.testvision.application.dto.GradingCriteriaDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.SubmissionDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.StatisticsDTO;

import java.util.List;

public class TestDTO {

    private Long id;
    private List<String> questions;
    private int totalPoints;
    private GradingCriteriaDTO gradingCriteria;
    private List<SubmissionDTO> submissions;
    private StatisticsDTO statistics;

    public TestDTO() {
    }

    public TestDTO(Long id, List<String> questions, int totalPoints, GradingCriteriaDTO gradingCriteria, List<SubmissionDTO> submissions, StatisticsDTO statistics) {
        this.id = id;
        this.questions = questions;
        this.totalPoints = totalPoints;
        this.gradingCriteria = gradingCriteria;
        this.submissions = submissions;
        this.statistics = statistics;
    }

    public Long getId() {
        return id;
    }

    public List<String> getQuestions() {
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
}
