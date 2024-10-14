package nl.hu.inno.hulp.exam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRabbit
public class ExamConfig {
    public static final String QUEUE_NAME = "examQueue";

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    public Queue examQueue() {
        return new Queue(QUEUE_NAME, true);
    }

//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.activateDefaultTyping(
//                BasicPolymorphicTypeValidator.builder()
//                        .allowIfSubType("nl.hu.inno.hulp.commons.response")
//                        .allowIfSubType("nl.hu.inno.hulp.commons.dto")
//                        .build(),
//                ObjectMapper.DefaultTyping.NON_FINAL
//        );
//        return new Jackson2JsonMessageConverter(objectMapper);
//    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }

//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(QUEUE_NAME);
//        return container;
//    }

//    @Bean
//    public MessageListenerAdapter listenerAdapter(ExamReceiver receiver, MessageConverter jsonMessageConverter) {
//        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, jsonMessageConverter);
//        adapter.setDefaultListenerMethod("receiveMessage");
//        return adapter;
//    }
}
