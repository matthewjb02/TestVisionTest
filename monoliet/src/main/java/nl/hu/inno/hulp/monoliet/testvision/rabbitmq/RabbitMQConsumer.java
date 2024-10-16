package nl.hu.inno.hulp.monoliet.testvision.rabbitmq;


import nl.hu.inno.hulp.monoliet.testvision.application.service.ExamService;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.ExamResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.SubmissionResponse;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class RabbitMQConsumer {

    private final ExamService examService;

    @Autowired
    public RabbitMQConsumer(ExamService examService) {
        this.examService = examService;
    }

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = "${rabbit.grading.queue}")
    public void consumeMessage(String string) {
        LOGGER.info("Message received: {}", string);
    }

//    @RabbitListener(queues = "${rabbit.grading.queue.name}")
//    public ExamResponse consumeExamById(Long examId) {
//        LOGGER.info("Received request for exam by examId: {}", examId);
//
//        return examService.getExamById(examId);
//    }
//
//    @RabbitListener(queues = "${rabbit.grading.queue.name}")
//    public List<SubmissionResponse> consumeSubmissionsByExamId(Long examId) {
//        LOGGER.info("Received request for submissions by examId: {}", examId);
//
//        return examService.getSubmissionsByExamId(examId);
//    }
}
