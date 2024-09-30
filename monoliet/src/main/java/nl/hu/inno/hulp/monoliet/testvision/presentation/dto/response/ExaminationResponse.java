package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import nl.hu.inno.hulp.monoliet.testvision.application.dto.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.Examination;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamState;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExaminationResponse {
    private final ExamDTO exam;

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

        this.exam = new ExamDTO(examination.getExam().getId(),
                                getQuestionDTOs(examination.getExam().getQuestions()),
                                examination.getExam().getTotalPoints(),
                                examination.getExam().getMakerMail(),
                                examination.getExam().getExamValidatorMail(),
                                examination.getExam().getValidationStatus(),
                                examination.getExam().getReason(),
                                gradingCriteriaDTO,
                                submissionDTOs,
                                statisticsDTO
                );
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
                        mcQuestion.getGivenPoints(),
                        mcQuestion.getAnswers(),
                        mcQuestion.getCorrectAnswerIndex(),
                        mcQuestion.getAnswer()));
            } else {
                OpenQuestion openQuestion = (OpenQuestion)question;

                dtos.add(new OpenQuestionDTO(
                        openQuestion.getId(),
                        openQuestion.getPoints(),
                        openQuestion.getQuestion(),
                        openQuestion.getGivenPoints(),
                        openQuestion.getTeacherFeedback(),
                        openQuestion.getCorrectAnswer(),
                        openQuestion.getAnswer()));
            }
        }
        return dtos;
    }

    public ExamDTO getExam() {
        return exam;
    }
}
