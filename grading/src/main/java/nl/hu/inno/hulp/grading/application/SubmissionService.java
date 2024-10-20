package nl.hu.inno.hulp.grading.application;


import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.commons.messaging.CourseDTO;
import nl.hu.inno.hulp.commons.messaging.ExamSessionDTO;
import nl.hu.inno.hulp.commons.messaging.SubmissionDTO;
import nl.hu.inno.hulp.commons.request.GradingRequest;
import nl.hu.inno.hulp.commons.request.UpdateQuestionGradingRequest;
import nl.hu.inno.hulp.commons.response.TeacherResponse;
import nl.hu.inno.hulp.grading.data.SubmissionRepository;
import nl.hu.inno.hulp.grading.domain.Grading;
import nl.hu.inno.hulp.grading.domain.Submission;
import nl.hu.inno.hulp.grading.rabbitmq.RabbitMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
        SubmissionDTO submissionDto = rabbitMQProducer.requestSubmissionByExamAndStudentId(examId, studentId);
        return submissionRepository.findById(submissionDto.getId()).orElseThrow();
    }

    public List<Submission> getSubmissionsByExamId(Long examId) {
        LOGGER.info("Sending request for getting submissions by examId: {}", examId);
        List<Submission> submissionList = new ArrayList<>();
        List<SubmissionDTO> submissionDTOList = rabbitMQProducer.requestSubmissionsByExamId(examId);
        for (SubmissionDTO submissionDTO : submissionDTOList) {
            submissionList.add(submissionRepository.findById(submissionDTO.getId()).orElseThrow());
        }
        return submissionList;
    }


    public void updateOpenQuestionGrading(Long examId, Long studentId, int questionNr, UpdateQuestionGradingRequest request) {


        Submission submission = findSubmissionByExamAndStudentId(examId, studentId);
        ExamSessionDTO examSession = submission.getExamSession();

        // update submission examession quesiotn points with messaging
        LOGGER.info("Sending request for updating examSession question points: {}", examSession.getId());
        rabbitMQProducer.requestUpdateQuestionPoints(examSession.getId(), questionNr, request);


        submissionRepository.save(submission);
    }

    public void addGrading(Long examId, Long studentId, GradingRequest request) {
        Submission submission = findSubmissionByExamAndStudentId(examId, studentId);

        CourseDTO examCourse = rabbitMQProducer.requestCourseByExamId(examId);
        if (examCourse.getTeachers().stream().noneMatch(teacherDTO -> teacherDTO.getId().equals(request.getTeacherId()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Teacher is not allowed to grade this exam");
        }

        String teacherUrl = "http://localhost:8085/teacher/" + request.getTeacherId();
        TeacherResponse teacher = restTemplate.getForObject(teacherUrl, TeacherResponse.class);

        Long exam = submission.getExamSession().getExam();
        String calculateGradeUrl = "http://localhost:8085/courses/{courseId}/exams/grade_calculation/" + exam;
        Double calculatedGrade = restTemplate.getForObject(calculateGradeUrl, Double.class);

        Grading grading = Grading.createGrading(calculatedGrade, request.getComments());

        grading.addGrader(teacher.getId());
        submission.addGrading(grading);

        // after the final grade we update the exam statistics
        LOGGER.info("Sending request for updating statistics for exam {}", exam);
        rabbitMQProducer.requestUpdateStatistics(exam);

        submissionRepository.save(submission);
    }

    public void add(Submission submission) {
        submissionRepository.save(submission);
    }

}

