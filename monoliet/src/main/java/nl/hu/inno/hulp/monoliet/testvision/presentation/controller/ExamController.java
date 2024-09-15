package nl.hu.inno.hulp.monoliet.testvision.presentation.controller;

import nl.hu.inno.hulp.monoliet.testvision.application.service.ExamService;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.ExamInactiveException;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NoExamFoundException;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.AnswerRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.ExamRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.SeeQuestion;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.StartExamRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.ExamResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.QuestionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/exam")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/start")
    public ExamResponse startExam(@RequestBody StartExamRequest examRequest) {
        try {
            return new ExamResponse(examService.startExam(examRequest));
        } catch(NoExamFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/seeQuestion")
    public QuestionResponse seeQuestion(@RequestBody SeeQuestion examRequest) {
        try {
            return new QuestionResponse(examService.seeQuestion(examRequest));
        } catch(ExamInactiveException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NoExamFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/answer")
    public ExamResponse enterAnswer(@RequestBody AnswerRequest answerRequest) {
        try {
            return new ExamResponse(examService.enterAnswer(answerRequest));
        } catch(ExamInactiveException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NoExamFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/end")
    public ExamResponse endExam(@RequestBody ExamRequest examRequest) {
        try {
            return new ExamResponse(examService.endExam(examRequest));
        } catch(ExamInactiveException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NoExamFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
