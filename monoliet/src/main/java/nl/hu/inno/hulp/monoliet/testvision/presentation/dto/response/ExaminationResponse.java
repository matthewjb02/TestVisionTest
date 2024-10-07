package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import nl.hu.inno.hulp.monoliet.testvision.application.dto.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.Examination;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamState;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExaminationResponse {
    private final ExamResponse exam;

    public ExaminationResponse(Examination examination) {
        GradingCriteriaDTO gradingCriteriaDTO = null;
        Exam exam = examination.getExam();
        if (exam.getGradingCriteria() != null) {
            gradingCriteriaDTO = new GradingCriteriaDTO(
                    exam.getGradingCriteria().getOpenQuestionWeight(),
                    exam.getGradingCriteria().getClosedQuestionWeight()
            );
        }

        List<SubmissionDTO> submissionDTOs = exam.getSubmissions().stream()
                .map(submission -> new SubmissionDTO(submission.getId(), submission.getStatus()))
                .collect(Collectors.toList());

        StatisticsDTO statisticsDTO = null;
        if (exam.getStatistics() != null) {
            statisticsDTO = new StatisticsDTO(
                    exam.getStatistics().getSubmissionCount(),
                    exam.getStatistics().getPassCount(),
                    exam.getStatistics().getFailCount(),
                    exam.getStatistics().getAverageScore()
            );
        }

        this.exam = new ExamResponse(exam);
    }

    private List<QuestionResponse> getQuestionResponses(List<QuestionEntity> questions) {
        List<QuestionResponse> responses = new ArrayList<>();

        for (QuestionEntity question : questions){
            if (question.getClass().equals(MultipleChoiceQuestion.class)){
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;

                responses.add(new MultipleChoiceQuestionResponse(mcQuestion));
            } else {
                OpenQuestion openQuestion = (OpenQuestion)question;

                responses.add(new OpenQuestionResponse(openQuestion));
            }
        }
        return responses;
    }

    public ExamResponse getExam() {
        return exam;
    }
}
