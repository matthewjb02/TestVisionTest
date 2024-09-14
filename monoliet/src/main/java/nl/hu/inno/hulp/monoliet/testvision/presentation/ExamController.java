package nl.hu.inno.hulp.monoliet.testvision.presentation;

import nl.hu.inno.hulp.monoliet.testvision.application.ExamService;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.ExamInactiveException;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NoExamFoundException;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.AnswerRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.ExamRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.ExamResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.StartExamRequest;
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
        return new ExamResponse(examService.startExam(examRequest));
    }

    @GetMapping("/seeQuestion")
    public void seeQuestion(@RequestBody ExamRequest examRequest) {
        try {
            examService.seeQuestion(examRequest);
        } catch(ExamInactiveException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NoExamFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/answer")
    public void enterAnswer(@RequestBody AnswerRequest answerRequest) {
        try {
            examService.enterAnswer(answerRequest);
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
