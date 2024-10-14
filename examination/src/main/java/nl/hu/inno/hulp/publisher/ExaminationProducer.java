package nl.hu.inno.hulp.publisher;

import nl.hu.inno.hulp.commons.request.AnswerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExaminationProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExaminationProducer.class);
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public ExaminationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendStudentRequest(Long studentId) {
        LOGGER.info(String.format("Message sent -> %s", studentId));
        rabbitTemplate.convertAndSend(exchange, routingKey, studentId);
    }

    public void sendExamRequest(Long examId) {
        LOGGER.info(String.format("Message sent -> %s", examId));
        rabbitTemplate.convertAndSend(exchange, routingKey, examId);
    }

    public void sendQuestionRequest(Long questionId) {
        LOGGER.info(String.format("Message sent -> %s", questionId));
        rabbitTemplate.convertAndSend(exchange, routingKey, questionId);
    }

    public void sendAnswerRequest(AnswerRequest answerRequest) {
        LOGGER.info(String.format("Message sent -> %s", answerRequest));
        rabbitTemplate.convertAndSend(exchange, routingKey, answerRequest);
    }
}