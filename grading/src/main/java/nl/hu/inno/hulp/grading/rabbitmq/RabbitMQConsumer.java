package nl.hu.inno.hulp.grading.rabbitmq;



import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RabbitMQConsumer {


    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = "${rabbit.monoliet.demo.queue}")
    public void consumeNoteMessage(String string) {
        LOGGER.info("Message received: {}", string);
    }

}
