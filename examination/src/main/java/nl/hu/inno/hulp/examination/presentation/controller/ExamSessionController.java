package nl.hu.inno.hulp.examination.presentation.controller;

import nl.hu.inno.hulp.commons.exception.ExaminationInactiveException;
import nl.hu.inno.hulp.commons.exception.NoExamSessionFoundException;
import nl.hu.inno.hulp.commons.exception.NotAllowedException;
import nl.hu.inno.hulp.commons.exception.PasswordIncorrectException;
import nl.hu.inno.hulp.commons.request.AnswerRequest;
import nl.hu.inno.hulp.commons.request.ExamSessionRequest;
import nl.hu.inno.hulp.commons.request.SeeQuestion;
import nl.hu.inno.hulp.commons.request.StartExamSession;
import nl.hu.inno.hulp.commons.response.ExamSessionResponse;
import nl.hu.inno.hulp.commons.response.QuestionResponse;
import nl.hu.inno.hulp.examination.application.service.ExamSessionService;
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
