package nl.hu.inno.hulp.commons.response;

import lombok.Getter;
import nl.hu.inno.hulp.commons.dto.GradingCriteriaDTO;

import java.util.List;

@Getter
public class ExamResponse {
    private final Long id;
    private final int totalPoints;
    private final List<QuestionResponse> questions;
    private final List<SubmissionResponse> submissions;
    private final GradingCriteriaDTO gradingCriteria;
    private final StatisticsResponse statisticsResponse;

    public ExamResponse(Long id, int totalPoints, List<QuestionResponse> questions, List<SubmissionResponse> submissions, GradingCriteriaDTO gradingCriteria, StatisticsResponse statisticsResponse) {
        this.id = id;
        this.totalPoints = totalPoints;
        this.questions = questions;
        this.submissions = submissions;
        this.gradingCriteria = gradingCriteria;
        this.statisticsResponse = statisticsResponse;
    }
}