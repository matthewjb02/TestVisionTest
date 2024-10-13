package nl.hu.inno.hulp.exam.application.service;

import nl.hu.inno.hulp.commons.dto.GradingCriteriaDTO;
import nl.hu.inno.hulp.commons.dto.StatisticsDTO;
import nl.hu.inno.hulp.commons.dto.SubmissionDTO;
import nl.hu.inno.hulp.commons.response.*;
import nl.hu.inno.hulp.exam.data.ExamRepository;
import nl.hu.inno.hulp.exam.data.QuestionRepository;
import nl.hu.inno.hulp.exam.domain.Exam;
import nl.hu.inno.hulp.exam.domain.GradingCriteria;
import nl.hu.inno.hulp.exam.domain.Statistics;
import nl.hu.inno.hulp.exam.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.exam.domain.question.OpenQuestion;
import nl.hu.inno.hulp.exam.domain.question.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public ExamService(ExamRepository examRepository, QuestionRepository questionRepository, RestTemplate restTemplate) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.restTemplate = restTemplate;
    }

    public ExamResponse addExam(long examMakerId, long examValidatorId) {
        Exam exam = new Exam(examMakerId, examValidatorId);

        Statistics statistics = Statistics.createStatistics(0, 0, 0, 0);
        exam.setStatistics(statistics);
        Exam savedExam = examRepository.save(exam);

        return toExamResponse(savedExam);
    }

    public ExamResponse deleteExam(Long id) {
        ExamResponse oldExamDTO = getExamById(id);
        examRepository.deleteById(id);
        return oldExamDTO;
    }

    public List<ExamResponse> getAllExams() {
        List<Exam> allExams = examRepository.findAll();
        List<ExamResponse> examDTOS = new ArrayList<>();
        for (Exam exam : allExams) {
            examDTOS.add(toExamResponse(exam));
        }

        return examDTOS;
    }

    public ExamResponse getExamById(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No exam with id: " + id + " found!"));

        return toExamResponse(exam);
    }

    public Exam getExam(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No exam with id: " + id + " found!"));
        return exam;
    }

    public ExamResponse addQuestionsByIdsToExam(Long examId, List<Long> questionIds) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam not found"));
        List<QuestionEntity> newQuestions = questionRepository.findAllById(questionIds);
        exam.getQuestions().addAll(newQuestions);
        exam.calculateTotalPoints();
        examRepository.save(exam);
        return toExamResponse(exam);
    }

    public ExamResponse addGradingCriteriaToExam(Long examId, GradingCriteriaDTO gradingCriteriaDTO) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam not found"));

        GradingCriteria gradingCriteria = new GradingCriteria(
                gradingCriteriaDTO.openQuestionWeight(),
                gradingCriteriaDTO.closedQuestionWeight()
        );

        exam.setGradingCriteria(gradingCriteria);
        examRepository.save(exam);

        return toExamResponse(exam);
    }

    private ExamResponse toExamResponse(Exam exam) {

        GradingCriteriaDTO gradingCriteriaDTO = new  GradingCriteriaDTO(0,0);
        if (exam.getGradingCriteria() != null) {
            gradingCriteriaDTO = new GradingCriteriaDTO(
                    exam.getGradingCriteria().getOpenQuestionWeight(),
                    exam.getGradingCriteria().getClosedQuestionWeight()
            );
        }

        List<SubmissionResponse> submissionResponses = exam.getSubmissionIds().stream()
                .map(submissionId -> getSubmissionById(submissionId))
                .collect(Collectors.toList());


        StatisticsResponse statisticsResponse = new StatisticsResponse(0, 0, 0, 0);
        if (exam.getStatistics() != null) {
            statisticsResponse = new StatisticsResponse(exam.getStatistics().getSubmissionCount(),
                    exam.getStatistics().getPassCount(),
                    exam.getStatistics().getFailCount(),
                    exam.getStatistics().getAverageScore()
            );
        }
       
        return new ExamResponse(exam.getId(), exam.getTotalPoints(),
              getQuestionResponses(exam.getQuestions()), submissionResponses, gradingCriteriaDTO, statisticsResponse);
    }

    private List<QuestionResponse> getQuestionResponses(List<QuestionEntity> questions) {
        List<QuestionResponse> responses = new ArrayList<>();

        for (QuestionEntity question : questions){
            if (question.getClass().equals(MultipleChoiceQuestion.class)){
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;

                responses.add(new MultipleChoiceQuestionResponse(mcQuestion.getPoints(),
                        mcQuestion.getQuestion(), mcQuestion.getAnswers(),
                        mcQuestion.getCorrectAnswerIndexes(), mcQuestion.getGivenAnswers()));
            } else {
                OpenQuestion openQuestion = (OpenQuestion)question;

                responses.add(new OpenQuestionResponse(openQuestion.getPoints(),
                        openQuestion.getQuestion(), openQuestion.getCorrectAnswer(),
                        openQuestion.getAnswer(), openQuestion.getTeacherFeedback()));
            }
        }
        return responses;
    }

    public SubmissionResponse getSubmissionById(Long id) {
        String url = "http://localhost:8080/submission/" + id;

        ResponseEntity<SubmissionResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});

        return response.getBody();
    }
}
