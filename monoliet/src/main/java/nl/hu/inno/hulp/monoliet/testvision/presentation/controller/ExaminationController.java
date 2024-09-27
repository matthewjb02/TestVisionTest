package nl.hu.inno.hulp.monoliet.testvision.presentation.controller;

import nl.hu.inno.hulp.monoliet.testvision.application.service.ExaminationService;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.ExaminationInactiveException;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NoExaminationFoundException;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.AnswerRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.ExaminationRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.SeeQuestion;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.StartExaminationRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.ExaminationResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.QuestionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/examination")
public class ExaminationController {
    private final ExaminationService examinationService;

    public ExaminationController(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @PostMapping("/start")
    public ExaminationResponse startExamination(@RequestBody StartExaminationRequest examRequest) {
        try {
            return new ExaminationResponse(examinationService.startExamination(examRequest));
        } catch(NoExaminationFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/seeQuestion")
    public QuestionResponse seeQuestion(@RequestBody SeeQuestion examRequest) {
        try {
            return new QuestionResponse(examinationService.seeQuestion(examRequest));
        } catch(ExaminationInactiveException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NoExaminationFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/answer")
    public ExaminationResponse enterAnswer(@RequestBody AnswerRequest answerRequest) {
        try {
            return new ExaminationResponse(examinationService.enterAnswer(answerRequest));
        } catch(ExaminationInactiveException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NoExaminationFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/end")
    public ExaminationResponse endExamination(@RequestBody ExaminationRequest examinationRequest) {
        try {
            return new ExaminationResponse(examinationService.endExam(examinationRequest));
        } catch(ExaminationInactiveException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NoExaminationFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
