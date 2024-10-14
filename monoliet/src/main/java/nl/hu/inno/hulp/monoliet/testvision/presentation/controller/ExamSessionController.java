package nl.hu.inno.hulp.monoliet.testvision.presentation.controller;

import nl.hu.inno.hulp.monoliet.testvision.application.service.ExamSessionService;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.ExaminationInactiveException;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NoExamSessionFoundException;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NotAllowedException;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.PasswordIncorrectException;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.AnswerRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.ExamSessionRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.SeeQuestion;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.StartExamSession;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.ExamSessionResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.QuestionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/session")
public class ExamSessionController {
    private final ExamSessionService examSessionService;

    public ExamSessionController(ExamSessionService examSessionService) {
        this.examSessionService = examSessionService;
    }

    @PostMapping("/start")
    public ExamSessionResponse startExamSession(@RequestBody StartExamSession examRequest) {
        try {
            return new ExamSessionResponse(examSessionService.startExamSession(examRequest));
        } catch(NotAllowedException | PasswordIncorrectException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch(NoExamSessionFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

//    @GetMapping("/seeQuestion/")
//    public QuestionResponse seeQuestion(@RequestBody SeeQuestion examRequest) {
//        try {
//            return new QuestionResponse(examSessionService.seeQuestion(examRequest));
//        } catch(ExaminationInactiveException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
//        } catch (NoExamSessionFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }
//    }
@GetMapping("/seeQuestion/{questionId}/{examId}")
public QuestionResponse seeQuestion(@PathVariable long examId, @PathVariable int questionId) {
    SeeQuestion examRequest = new SeeQuestion(examId, questionId);
    try {
        return new QuestionResponse(examSessionService.seeQuestion(examRequest));
    } catch(ExaminationInactiveException e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (NoExamSessionFoundException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
}

    @PatchMapping("/answer")
    public ExamSessionResponse enterAnswer(@RequestBody AnswerRequest answerRequest) {
        try {
            return new ExamSessionResponse(examSessionService.enterAnswer(answerRequest));
        } catch(ExaminationInactiveException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NoExamSessionFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/end")
    public ExamSessionResponse endExamSession(@RequestBody ExamSessionRequest examinationRequest) {
        try {
            return new ExamSessionResponse(examSessionService.endExamSession(examinationRequest));
        } catch(ExaminationInactiveException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NoExamSessionFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
