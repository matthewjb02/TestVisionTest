package nl.hu.inno.hulp.grading.application;


import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.commons.messaging.CourseDTO;
import nl.hu.inno.hulp.commons.messaging.ExamSessionDTO;
import nl.hu.inno.hulp.commons.messaging.SubmissionDTO;
import nl.hu.inno.hulp.commons.request.GradingRequest;
import nl.hu.inno.hulp.commons.response.ExamSessionResponse;
import nl.hu.inno.hulp.commons.response.GradingResponse;
import nl.hu.inno.hulp.commons.response.SubmissionResponse;
import nl.hu.inno.hulp.commons.response.TeacherResponse;
import nl.hu.inno.hulp.grading.data.SubmissionRepository;
import nl.hu.inno.hulp.grading.domain.Grading;
import nl.hu.inno.hulp.grading.domain.Submission;
import nl.hu.inno.hulp.grading.rabbitmq.RabbitMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
public class SubmissionService {


    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    private final RabbitMQProducer rabbitMQProducer;
    private final RestTemplate restTemplate = new RestTemplate();
    private final SubmissionRepository submissionRepository;

    public SubmissionService(RabbitMQProducer rabbitMQProducer, SubmissionRepository submissionRepository) {
        this.rabbitMQProducer = rabbitMQProducer;
        this.submissionRepository = submissionRepository;
    }


    private Submission findSubmissionByExamAndStudentId(Long examId, Long studentId) {
        String submissionUrl = "http://localhost:8086/exams/{examId}/students/{studentId}/submission";
        SubmissionResponse submissionDto = restTemplate.getForObject(submissionUrl, SubmissionResponse.class, examId, studentId);
        Long submissionId = submissionDto.getId();
        return submissionRepository.findById(submissionDto.getId()).orElseThrow();
    }

    public SubmissionResponse getSubmissionResponseById(Long id) {
        return toSubmissionResponse(id);
    }

    public List<SubmissionResponse> getSubmissionsByExamId(Long examId) {
        String submissionUrl = "http://localhost:8086/exams/{examId}/submissions";
        ResponseEntity<List<SubmissionResponse>> response = restTemplate.exchange(
                submissionUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                examId
        );
        return response.getBody();
    }

//
//    public void updateOpenQuestionGrading(Long examId, Long studentId, int questionNr, UpdateQuestionGradingRequest request) {
//
//
//        Submission submission = findSubmissionByExamAndStudentId(examId, studentId);
//        ExamSessionDTO examSession = submission.getExamSession();
//
//        // update submission examession quesiotn points with messaging
//        LOGGER.info("Sending request for updating examSession question points: {}", examSession.getId());
//        rabbitMQProducer.requestUpdateQuestionPoints(examSession.getId(), questionNr, request);
//
//
//        submissionRepository.save(submission);
//    }
//
//    public void addGrading(Long examId, Long studentId, GradingRequest request) {
//        Submission submission = findSubmissionByExamAndStudentId(examId, studentId);
//
//        CourseDTO examCourse = rabbitMQProducer.requestCourseByExamId(examId);
//        if (examCourse.getTeachers().stream().noneMatch(teacherDTO -> teacherDTO.getId().equals(request.getTeacherId()))) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Teacher is not allowed to grade this exam");
//        }
//
//        String teacherUrl = "http://localhost:8085/teacher/" + request.getTeacherId();
//        TeacherResponse teacher = restTemplate.getForObject(teacherUrl, TeacherResponse.class);
//
//        Long exam = submission.getExamSession().getExam();
//        String calculateGradeUrl = "http://localhost:8085/courses/{courseId}/exams/grade_calculation/" + exam;
//        Double calculatedGrade = restTemplate.getForObject(calculateGradeUrl, Double.class);
//
//        Grading grading = Grading.createGrading(calculatedGrade, request.getComments());
//
//        grading.addGrader(teacher.getId());
//        submission.addGrading(grading);
//
//        // after the final grade we update the exam statistics
//        LOGGER.info("Sending request for updating statistics for exam {}", exam);
//        rabbitMQProducer.requestUpdateStatistics(exam);
//
//        submissionRepository.save(submission);
//    }
//
//    public void add(Submission submission) {
//        submissionRepository.save(submission);
//    }


    // helper functions

    public SubmissionResponse toSubmissionResponse(Long id){
        Submission submission = submissionRepository.findById(id).orElseThrow();
        String examSessionUrl = "http://localhost:8086/session/" + submission.getExamSessionId();
        ExamSessionResponse examSession = restTemplate.getForObject(examSessionUrl, ExamSessionResponse.class);

        return new SubmissionResponse(examSession, submission.getId(), submission.getStatus(), toGradingsResponse(submission.getGrading()));

    }

    public GradingResponse toGradingsResponse(Grading grading) {
        return new GradingResponse(grading.getId(), grading.getGrade(), grading.getComments());

    }




}

