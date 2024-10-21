package nl.hu.inno.hulp.exam;

import nl.hu.inno.hulp.commons.response.ExamResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ExamProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendExam(ExamResponse examResponse){
        rabbitTemplate.convertAndSend(ExamConfig.QUEUE_NAME, examResponse);
    }
}
