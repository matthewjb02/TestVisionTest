package nl.hu.inno.hulp.monoliet.testvision.application.service;

import nl.hu.inno.hulp.monoliet.testvision.application.dto.*;
import nl.hu.inno.hulp.monoliet.testvision.data.CourseRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.QuestionRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.TeacherRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.ExamRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.GradingCriteria;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Statistics;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.ExamResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.MultipleChoiceQuestionResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.OpenQuestionResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.QuestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {
//todo fix maker/validator assigning
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public ExamService(ExamRepository examRepository, QuestionRepository questionRepository, TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }
    public ExamResponse addExam(Exam exam, long examMakerId, long examValidatorId, long courseId) {
        Teacher  maker=teacherRepository.findById(examMakerId).orElseThrow();
        Teacher examValidator=teacherRepository.findById(examValidatorId).orElseThrow();
        exam.addExamValidator(examValidator);
        exam.addExamMaker(maker);

        Statistics statistics = Statistics.createStatistics(0, 0, 0, 0);
        exam.addStatistics(statistics);
        Exam savedExam = examRepository.save(exam);

        return new ExamResponse(exam);
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
            examDTOS.add(new ExamResponse(exam));
        }

        return examDTOS;
    }

    public ExamResponse getExamById(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No exam with id: " + id + " found!"));

        return new ExamResponse(exam);
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
        return new ExamResponse(exam);
    }

    public ExamResponse addGradingCriteriaToExam(Long examId, GradingCriteriaDTO gradingCriteriaDTO) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam not found"));

        GradingCriteria gradingCriteria = new GradingCriteria(
                gradingCriteriaDTO.openQuestionWeight(),
                gradingCriteriaDTO.closedQuestionWeight()
        );

        exam.addGradingCriteria(gradingCriteria);
        examRepository.save(exam);

        return new ExamResponse(exam);
    }

    private ExamResponse toDTO(Exam exam) {

        GradingCriteriaDTO gradingCriteriaDTO = new  GradingCriteriaDTO(0,0);
        if (exam.getGradingCriteria() != null) {
            gradingCriteriaDTO = new GradingCriteriaDTO(
                    exam.getGradingCriteria().getOpenQuestionWeight(),
                    exam.getGradingCriteria().getClosedQuestionWeight()
            );
        }

        List<SubmissionDTO> submissionDTOs = exam.getSubmissions().stream()
                .map(submission -> new SubmissionDTO(submission.getId(), submission.getStatus()))
                .collect(Collectors.toList());


        StatisticsDTO statisticsDTO = new StatisticsDTO(0, 0, 0, 0);
        if (exam.getStatistics() != null) {
            statisticsDTO = new StatisticsDTO(
                    exam.getStatistics().getSubmissionCount(),
                    exam.getStatistics().getPassCount(),
                    exam.getStatistics().getFailCount(),
                    exam.getStatistics().getAverageScore()
            );
        }

       
      return new ExamResponse(exam);
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


    public void saveExam(Exam exam) {
        examRepository.save(exam);
    }

}
