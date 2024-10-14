package nl.hu.inno.hulp.consumer;

import nl.hu.inno.hulp.commons.response.StudentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ExaminationConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExaminationConsumer.class);
    private StudentResponse studentResponse;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeStudentResponse(@Payload StudentResponse studentResponse) {
        LOGGER.info(String.format("Received JSON message -> %s", studentResponse));
        this.studentResponse = studentResponse;
    }

    public StudentResponse receiveStudentRequest() {
        return this.studentResponse;
    }
}