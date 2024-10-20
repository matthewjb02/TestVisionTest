package nl.hu.inno.hulp.monoliet.testvision.rabbitmq;


import nl.hu.inno.hulp.commons.messaging.CourseDTO;
import nl.hu.inno.hulp.commons.messaging.SubmissionDTO;
import nl.hu.inno.hulp.commons.request.SubmissionRequest;
import nl.hu.inno.hulp.commons.response.SubmissionResponse;
import nl.hu.inno.hulp.monoliet.testvision.application.service.CourseService;
import nl.hu.inno.hulp.monoliet.testvision.application.service.ExamService;
import nl.hu.inno.hulp.monoliet.testvision.application.service.ExamSessionService;
import nl.hu.inno.hulp.monoliet.testvision.application.service.TeacherService;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.UpdateQuestionGradingRequest;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



import java.util.List;

@Component
public class RabbitMQConsumer {

    private final ExamService examService;
    private final ExamSessionService examSessionService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    @Autowired
    public RabbitMQConsumer(ExamService examService, ExamSessionService examSessionService, TeacherService teacherService, CourseService courseService) {
        this.examService = examService;
        this.examSessionService = examSessionService;
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = "${rabbit.grading.demo.queue}")
    public void consumeMessage(String string) {
        LOGGER.info("Message received: {}", string);
    }

    @RabbitListener(queues = "${rabbit.grading.exam.queue}")
    public SubmissionDTO consumeSubmissionByExamAndStudentId(SubmissionRequest request) {
        LOGGER.info("Received request for submission by examId: {} and studentId: {}", request.getExamId(), request.getStudentId());
        return examService.getSubmissionByExamAndStudentId(request.getExamId(), request.getStudentId());


    }

    @RabbitListener(queues = "${rabbit.grading.exam.queue}")
    public List<SubmissionDTO> consumeSubmissionsByExamId(Long examId) {
        LOGGER.info("Received request for submissions by examId: {}", examId);

        return examService.getSubmissionsByExamId(examId);
    }

    @RabbitListener(queues = "${rabbit.grading.examsession.name}")
    public void consumeUpdateOpenQuestionPoints(long examSessionId, int questionNr, UpdateQuestionGradingRequest request) {
        LOGGER.info("Received request for updating openQuestion points by submissionId: {} and openQuestionId: {}", examSessionId, questionNr, request);
        examSessionService.updatePointsOpenQuestion(examSessionId, questionNr, request);
    }

    @RabbitListener(queues = "${rabbit.grading.exam.queue}")
    public void consumeUpdateStatisticsForExam(long examId) {
        LOGGER.info("Received request for updating statistics for exam: {}", examId);
        examService.updateStatistics(examId);
    }


}