package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.GradingCriteriaDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.StatisticsDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.SubmissionDTO;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.ValidationStatus;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.QuestionResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ExamTeacherResponse {
    private Long id;
    private List<QuestionResponse> questions;
    private String examMaker;
    private String examValidator;
    private String validationStatus;
    private String reason;
    private int totalPoints;
    private GradingCriteriaDTO gradingCriteria;
    private List<SubmissionResponse> submissions;
    private StatisticsResponse statistics;

    public ExamTeacherResponse(Exam exam) {
        this.id = exam.getId();
        this.questions = new ArrayList<>();
        for (QuestionEntity question : exam.getQuestions()){
            questions.add(new QuestionResponse(question));
        }

        this.totalPoints = exam.getTotalPoints();
        this.examMaker = exam.getExamMaker().getEmail().getEmailString();
        this.examValidator = exam.getExamValidator().getEmail().getEmailString();
        this.validationStatus = exam.getValidationStatus().toString();
        this.reason = exam.getReason();
        this.gradingCriteria = new GradingCriteriaDTO(exam.getGradingCriteria().getOpenQuestionWeight(), exam.getGradingCriteria().getClosedQuestionWeight());
        this.submissions = new ArrayList<>();
        for (Submission submission : exam.getSubmissions()){
            submissions.add(new SubmissionResponse(submission));
        }
        this.statistics = new StatisticsResponse(exam.getStatistics());
    }
}
