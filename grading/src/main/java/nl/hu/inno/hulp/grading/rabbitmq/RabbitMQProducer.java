package nl.hu.inno.hulp.grading.rabbitmq;

import nl.hu.inno.hulp.commons.messaging.CourseDTO;
import nl.hu.inno.hulp.commons.messaging.SubmissionDTO;
import nl.hu.inno.hulp.commons.request.SubmissionRequest;
import nl.hu.inno.hulp.commons.request.UpdateOpenQuestionPoints;
import nl.hu.inno.hulp.commons.request.UpdateQuestionGradingRequest;
import nl.hu.inno.hulp.commons.response.CourseResponse;
import nl.hu.inno.hulp.commons.response.SubmissionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RabbitMQProducer {

    @Value("${rabbit.exchange.name}")
    private String exchangeName;

    @Value("${rabbit.routing.key}")
    private String routingKey;

    @Value("${rabbit.routing.key}")
    private String noteRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    public void sendMessage(String string) {

        LOGGER.info("Sending message: {}", string);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, string);
    }

    public void requestSubmissionByExamAndStudentId(Long examId, Long studentId) {
        LOGGER.info("Sending request for getting submission by examId: {} and studentId: {}", examId, studentId);
        SubmissionRequest request = new SubmissionRequest(examId, studentId);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, request);
    }

    public void requestUpdateQuestionPoints(Long examId, int questionNr, UpdateQuestionGradingRequest request) {
        LOGGER.info("Sending request for updating question points by examId: {} and questionNr: {}", examId, questionNr, request);
        UpdateOpenQuestionPoints updateOpenQuestionPointsRequest = new UpdateOpenQuestionPoints(examId, questionNr, request);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, updateOpenQuestionPointsRequest);
    }


    public void requestUpdateStatistics(Long exam) {
        LOGGER.info("Sending request for updating statistics by examId: {}", exam);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, exam);
    }
}