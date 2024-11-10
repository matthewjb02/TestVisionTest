package nl.hu.inno.hulp.exam;


import nl.hu.inno.hulp.commons.request.AddSubmissionToExam;
import nl.hu.inno.hulp.commons.request.UpdateOpenQuestionPoints;
import nl.hu.inno.hulp.commons.request.UpdateStatisticsRequest;
import nl.hu.inno.hulp.commons.response.ExamResponse;
import nl.hu.inno.hulp.exam.application.service.ExamService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ExamReceiver {

    private final ExamService examService;

    public ExamReceiver(ExamService examService) {
        this.examService = examService;
    }

    @RabbitListener(queues = ExamConfig.QUEUE_NAME)
    public void receiveMessage(ExamResponse examResponse){
        System.out.println(examResponse.getQuestions().get(1).getQuestion());
    }

    //@RabbitListener(queues = "${rabbitmq.examsession.exam.queue}")
    public void receiveUpdateOpenQuestionGradingMessage(UpdateOpenQuestionPoints message) {
        examService.updatePointsForOpenQuestion(message.getExamId(), message.getQuestionNr(), message.getGrading());
    }

    //@RabbitListener(queues = "${rabbit.grading.exam.queue}")
    public void receiveUpdateExamStatisticsMessage(Long examId) {
        examService.updateStatistics(examId);
    }

    //@RabbitListener(queues = "${rabbitmq.examsession.exam.queue}")
    public void receiveAddSubmissionToExamMessage(AddSubmissionToExam message) {
        examService.addSubmission(message.getExamId(), message.getSubmissionId());
    }
}

