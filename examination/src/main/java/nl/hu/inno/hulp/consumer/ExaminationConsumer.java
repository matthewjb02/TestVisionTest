package nl.hu.inno.hulp.consumer;

import nl.hu.inno.hulp.commons.response.StudentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ExaminationConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExaminationConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeStudentResponse(StudentResponse studentResponse) {
        LOGGER.info(String.format("Received JSON message -> %s", studentResponse));
    }
}