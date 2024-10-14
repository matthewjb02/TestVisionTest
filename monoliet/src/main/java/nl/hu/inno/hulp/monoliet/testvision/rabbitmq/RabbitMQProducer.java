package nl.hu.inno.hulp.monoliet.testvision.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Value("${rabbit.exchange.name}")
    private String exchangeName;

    @Value("${rabbit.routing.key}")
    private String routingKey;

    @Value("${rabbit.note.routing.key}")
    private String noteRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    public void sendUserMessage(String string) {

        LOGGER.info("Sending message: {}", string);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, string);
    }
}