package nl.hu.inno.hulp.grading.application;


import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.commons.request.GradingRequest;
import nl.hu.inno.hulp.commons.request.UpdateQuestionGradingRequest;
import nl.hu.inno.hulp.commons.response.*;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        String submissionUrl = "http://localhost:8086/exams/" + examId + "/students/" + studentId + "/submission";
        SubmissionResponse submissionResponse = restTemplate.getForObject(submissionUrl, SubmissionResponse.class, examId, studentId);
        Long submissionId = submissionResponse.getId();
        return submissionRepository.findById(submissionId).orElseThrow();
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


    public void updateOpenQuestionGrading(Long examId, Long studentId, int questionNr, UpdateQuestionGradingRequest request) {
        Submission submission = findSubmissionByExamAndStudentId(examId, studentId);
        Long examSessionIdFromSubmission = submission.getExamSessionId();
        String updateQuestionGradingUrl = "http://localhost:8083/session/" + examSessionIdFromSubmission + "/questions/" + questionNr + "/points";


        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("request", request);

        restTemplate.put(updateQuestionGradingUrl, requestBody);

        submissionRepository.save(submission);
    }

    public void addGrading(Long examId, Long studentId, GradingRequest request) {
        Submission submission = findSubmissionByExamAndStudentId(examId, studentId);

        String examCourseUrl = "http://localhost:8086/courses/exams/" + examId + "/course";
        CourseResponse examCourse = restTemplate.getForObject(examCourseUrl, CourseResponse.class, examId);


        if (examCourse.getTeachers().stream().noneMatch(teacherDTO -> teacherDTO.getId() == request.getTeacherId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Teacher is not allowed to grade this exam");
        }

        String teacherUrl = "http://localhost:8085/teacher/" + request.getTeacherId();
        TeacherResponse teacher = restTemplate.getForObject(teacherUrl, TeacherResponse.class);


        String calculatedGradeUrl = "http://localhost:8086/exams/" + examId + "/gradeCalculation";
        double calculatedGrade = restTemplate.getForObject(calculatedGradeUrl, Double.class, examId);

        Grading grading = Grading.createGrading(calculatedGrade, request.getComments());

        grading.addGrader(teacher.getId());
        submission.addGrading(grading);

        // after the final grade we update the exam statistics
        String updateStatisticsUrl = "http://localhost:8086/exams/" + examId + "/statistics";
        restTemplate.put(updateStatisticsUrl, null, examId);

        submissionRepository.save(submission);
    }


    public SubmissionResponse createSubmission(Long examSessionId) {
        Submission submission = Submission.createSubmission(examSessionId);
        SubmissionResponse submissionResponse = toSubmissionResponse(submission.getId());


        return toSubmissionResponse(submission.getId());

    }

    public void saveSubmission(Long id) {
        Submission submission = submissionRepository.findById(id).orElseThrow();
        submissionRepository.save(submission);
    }

    // helper functions

    public SubmissionResponse toSubmissionResponse(Long id){
        Submission submission = submissionRepository.findById(id).orElseThrow();
        String examSessionUrl = "http://localhost:8083/session/" + submission.getExamSessionId();
        ExamSessionResponse examSession = restTemplate.getForObject(examSessionUrl, ExamSessionResponse.class);

        return new SubmissionResponse(examSession, submission.getId(), submission.getStatus(), toGradingsResponse(submission.getGrading()));

    }

    public GradingResponse toGradingsResponse(Grading grading) {
        return new GradingResponse(grading.getId(), grading.getGrade(), grading.getComments());

    }






}

