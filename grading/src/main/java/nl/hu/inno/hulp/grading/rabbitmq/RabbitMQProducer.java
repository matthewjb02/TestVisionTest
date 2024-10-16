package nl.hu.inno.hulp.grading.rabbitmq;

import nl.hu.inno.hulp.commons.messaging.ExamDTO;
import nl.hu.inno.hulp.commons.response.ExamResponse;
import nl.hu.inno.hulp.commons.response.SubmissionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

    public ExamResponse requestExamById(Long examId) {
        LOGGER.info("Sending request for getting exam by examId: {}", examId);
        return (ExamResponse) rabbitTemplate.convertSendAndReceive(exchangeName, routingKey, examId);
    }

    public List<SubmissionResponse> requestSubmissionsByExamId(Long examId) {
        LOGGER.info("Sending request for getting submissions by examId: {}", examId);
        return (List<SubmissionResponse>) rabbitTemplate.convertSendAndReceive(exchangeName, routingKey, examId);
    }


}