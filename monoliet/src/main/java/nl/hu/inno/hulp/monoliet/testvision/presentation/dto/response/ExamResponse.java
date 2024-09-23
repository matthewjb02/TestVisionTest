package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import nl.hu.inno.hulp.monoliet.testvision.application.dto.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.State;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.test.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExamResponse {
    private final StudentResponse student;
    private final TestDTO test;
    private final State state;

    public ExamResponse(Exam exam) {
        this.student = new StudentResponse(exam.getStudent());

        GradingCriteriaDTO gradingCriteriaDTO = null;
        Test test = exam.getTest();
        if (test.getGradingCriteria() != null) {
            gradingCriteriaDTO = new GradingCriteriaDTO(
                    test.getGradingCriteria().getOpenQuestionWeight(),
                    test.getGradingCriteria().getClosedQuestionWeight()
            );
        }

        List<SubmissionDTO> submissionDTOs = test.getSubmissions().stream()
                .map(submission -> new SubmissionDTO(submission.getId(), submission.getStatus()))
                .collect(Collectors.toList());

        StatisticsDTO statisticsDTO = null;
        if (test.getStatistics() != null) {
            statisticsDTO = new StatisticsDTO(
                    test.getStatistics().getSubmissionCount(),
                    test.getStatistics().getPassCount(),
                    test.getStatistics().getFailCount(),
                    test.getStatistics().getAverageScore()
            );
        }

        this.test = new TestDTO(exam.getTest().getId(),
                                getQuestionDTOs(exam.getTest().getQuestions()),
                                exam.getTest().getTotalPoints(),
                                exam.getTest().getMakerMail(),
                                exam.getTest().getTestValidatorMail(),
                                exam.getTest().getValidationStatus(),
                                exam.getTest().getReason(),
                                gradingCriteriaDTO,
                                submissionDTOs,
                                statisticsDTO
                );
        this.state = exam.getState();
    }

    private List<QuestionDTO> getQuestionDTOs(List<Question> questions) {
        List<QuestionDTO> dtos = new ArrayList<>();

        for (Question question : questions){
            if (question.getClass().equals(MultipleChoiceQuestion.class)){
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;

                dtos.add(new MultipleChoiceQuestionDTO(
                        mcQuestion.getId(),
                        mcQuestion.getPoints(),
                        mcQuestion.getQuestion(),
                        mcQuestion.getAnswers(),
                        mcQuestion.getCorrectAnswerIndex(),
                        mcQuestion.getAnswer()));
            } else {
                OpenQuestion openQuestion = (OpenQuestion)question;

                dtos.add(new OpenQuestionDTO(
                        openQuestion.getId(),
                        openQuestion.getPoints(),
                        openQuestion.getQuestion(),
                        openQuestion.getCorrectAnswer(),
                        openQuestion.getAnswer()));
            }
        }
        return dtos;
    }


    public StudentResponse getStudent() {
        return student;
    }

    public TestDTO getTest() {
        return test;
    }

    public State getState() {
        return state;
    }
}
