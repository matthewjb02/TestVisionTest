package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.GradingCriteria;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ExamResponse {
    private final Long id;
    private final int totalPoints;
    private final List<QuestionResponse> questions;
    private final List<SubmissionResponse> submissions;
    private final GradingCriteria gradingCriteria;
    private final StatisticsResponse statisticsResponse;

    public ExamResponse(Exam exam) {
        this.id = exam.getId();
        this.totalPoints = exam.getTotalPoints();

        this.questions = new ArrayList<>();
        for (QuestionEntity question : exam.getQuestions()){
            questions.add(new QuestionResponse(question));
        }
        this.submissions = new ArrayList<>();
        exam.getSubmissions().forEach(submission -> submissions.add(new SubmissionResponse(submission.getExamSession(), submission.getId(), submission.getStatus(),submission.getGrading())));
        this.gradingCriteria=exam.getGradingCriteria();
        this.statisticsResponse=new StatisticsResponse(exam.getStatistics());
    }
}
