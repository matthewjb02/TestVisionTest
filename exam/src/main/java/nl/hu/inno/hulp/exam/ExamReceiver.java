package nl.hu.inno.hulp.exam;

import nl.hu.inno.hulp.commons.response.ExamResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ExamReceiver {

    @RabbitListener(queues = ExamConfig.QUEUE_NAME)
    public void receiveMessage(String examResponse){
        System.out.println(examResponse);
    }
}
