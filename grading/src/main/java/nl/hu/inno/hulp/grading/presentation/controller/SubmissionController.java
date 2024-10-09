// SubmissionController.java
package nl.hu.inno.hulp.grading.presentation.controller;

import nl.hu.inno.hulp.monoliet.testvision.application.service.SubmissionService;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.GradingRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.UpdateQuestionGradingRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.SubmissionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submission")
public class SubmissionController {

    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @GetMapping("/{testId}")
    public List<SubmissionResponse> getSubmissionsByTestId(@PathVariable Long testId) {
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