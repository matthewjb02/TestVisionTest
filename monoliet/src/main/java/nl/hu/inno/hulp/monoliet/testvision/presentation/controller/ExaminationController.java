package nl.hu.inno.hulp.monoliet.testvision.presentation.controller;

import nl.hu.inno.hulp.monoliet.testvision.application.service.ExaminationService;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.ExaminationInactiveException;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NoExaminationFoundException;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.*;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.CandidatesResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.ExamSessionResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.ExaminationResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.QuestionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/examination")
public class ExaminationController {
    private final ExaminationService examinationService;

    public ExaminationController(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @GetMapping("{id}")
    public ExaminationResponse getExamination(@PathVariable Long id) {
        try {
            return new ExaminationResponse(examinationService.getExaminationById(id));
        } catch (NoExaminationFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/create")
    public ExaminationResponse createExamination(@RequestBody CreateExamination createExamination) {
        return new ExaminationResponse(examinationService.createExamination(createExamination));
    }

    @PatchMapping("/candidates")
    public CandidatesResponse selectCandidates(@RequestBody Candidates candidates) {
        return new CandidatesResponse(examinationService.selectCandidates(candidates));
    }

    @PatchMapping("/candidate")
    public CandidatesResponse selectCandidate(@RequestBody Candidate candidate) {
        return new CandidatesResponse(examinationService.selectCandidate(candidate));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteExamination(@PathVariable Long id) {
        examinationService.deleteExamination(id);
    }
}
