package nl.hu.inno.hulp.grading.presentation;


import nl.hu.inno.hulp.commons.messaging.SubmissionDTO;
import nl.hu.inno.hulp.grading.application.SubmissionService;
import nl.hu.inno.hulp.commons.request.GradingRequest;
import nl.hu.inno.hulp.commons.request.UpdateQuestionGradingRequest;
import nl.hu.inno.hulp.commons.response.SubmissionResponse;
import nl.hu.inno.hulp.grading.domain.Submission;
import nl.hu.inno.hulp.grading.rabbitmq.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submission")
public class SubmissionController {

    private final SubmissionService submissionService;

    private final RabbitMQProducer rabbitMQProducer;

    public SubmissionController(SubmissionService submissionService, RabbitMQProducer rabbitMQProducer) {
        this.submissionService = submissionService;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @GetMapping("/{testId}")
    public List<Submission> getSubmissionsByTestId(@PathVariable Long testId) {
        return submissionService.getSubmissionsByExamId(testId);
    }


    @PutMapping("/{testId}/{studentId}/question/{questionNr}")
    public void updateQuestionGrading(@PathVariable Long testId, @PathVariable Long studentId, @PathVariable int questionNr, @RequestBody UpdateQuestionGradingRequest request) {

        submissionService.updateOpenQuestionGrading(testId, studentId, questionNr, request);
    }

    @PostMapping("/{testId}/{studentId}/grading")
    public void addGrading(@PathVariable Long testId, @PathVariable Long studentId, @RequestBody GradingRequest request) {
        submissionService.addGrading(testId, studentId, request);
    }



}