package nl.hu.inno.hulp.exam;


import nl.hu.inno.hulp.commons.request.AddSubmissionToExam;
import nl.hu.inno.hulp.commons.request.UpdateOpenQuestionPoints;
import nl.hu.inno.hulp.commons.response.ExamResponse;
import nl.hu.inno.hulp.commons.response.TeacherResponse;
import nl.hu.inno.hulp.exam.application.service.ExamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ExamConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExamConsumer.class);
    private final ExamService examService;

    public ExamConsumer(ExamService examService) {
        this.examService = examService;
    }


    @RabbitListener(queues = {"examination"})
    public void consumeExamId(Long examId){
        LOGGER.info(String.format("Received message -> %s", examId));
        examService.sendAndProcessExam(examId);
    }
    @RabbitListener(queues={"${rabbitmq.queue.name}"})
    public void consumeTeacherResponse(@Payload TeacherResponse teacherResponse){
        LOGGER.info(String.format("Received JSON message teacherResponse -> %s", teacherResponse));
    }

    //@RabbitListener(queues = "${rabbitmq.examsession.exam.queue}")
    public void receiveUpdateOpenQuestionGradingMessage(UpdateOpenQuestionPoints message) {
        examService.updatePointsForOpenQuestion(message.getExamId(), message.getQuestionNr(), message.getGrading());
    }

    //@RabbitListener(queues = "${rabbit.grading.exam.queue}")
    public void receiveUpdateExamStatisticsMessage(Long examId) {
        examService.updateStatistics(examId);
    }

    //@RabbitListener(queues = "${rabbitmq.examsession.exam.queue}")
    public void receiveAddSubmissionToExamMessage(AddSubmissionToExam message) {
        examService.addSubmission(message.getExamId(), message.getSubmissionId());
    }
}

