package nl.hu.inno.hulp.grading.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Value("${rabbit.exchange.name}")
    private String exchangeName;

    @Value("${rabbit.routing.key}")
    private String routingKey;

    @Value("${rabbit.demo.queue}")
    private String demoQueue;

    @Value("${rabbit.exam.queue}")
    private String examQueue;

    @Value("${rabbit.examsession.queue}")
    private String examSessionQueue;

    @Bean
    public Queue demoQueue() {
        return new Queue(demoQueue);
    }

    @Bean
    public Queue examQueue() {
        return new Queue(examQueue);
    }

    @Bean
    public Queue examSessionQueue() {
        return new Queue(examSessionQueue);
    }


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding demoBinding(Queue demoQueue, TopicExchange exchange) {
        return BindingBuilder.bind(demoQueue).to(exchange).with(routingKey);
    }

    @Bean
    public Binding examBinding(Queue examQueue, TopicExchange exchange) {
        return BindingBuilder.bind(examQueue).to(exchange).with(routingKey);
    }

    @Bean
    public Binding examSessionBinding(Queue examSessionQueue, TopicExchange exchange) {
        return BindingBuilder.bind(examSessionQueue).to(exchange).with(routingKey);
    }


    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}