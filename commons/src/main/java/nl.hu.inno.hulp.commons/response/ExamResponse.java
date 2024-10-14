package nl.hu.inno.hulp.commons.response;

import lombok.Getter;
import nl.hu.inno.hulp.commons.dto.GradingCriteriaDTO;

import java.util.List;

@Getter
public class ExamResponse {
    private Long id;
    private int totalPoints;
    private List<QuestionResponse> questions;
    private List<SubmissionResponse> submissions;
    private GradingCriteriaDTO gradingCriteria;
    private StatisticsResponse statisticsResponse;

    public ExamResponse() {
    }

    public ExamResponse(Long id, int totalPoints, List<QuestionResponse> questions, List<SubmissionResponse> submissions, GradingCriteriaDTO gradingCriteria, StatisticsResponse statisticsResponse) {
        this.id = id;
        this.totalPoints = totalPoints;
        this.questions = questions;
        this.submissions = submissions;
        this.gradingCriteria = gradingCriteria;
        this.statisticsResponse = statisticsResponse;
    }
}