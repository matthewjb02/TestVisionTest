package nl.hu.inno.hulp.monoliet.testvision.presentation.controller;

import nl.hu.inno.hulp.monoliet.testvision.application.dto.QuestionDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.service.QuestionService;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<QuestionDTO> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public QuestionDTO getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    @PostMapping
    public QuestionDTO addQuestion(@RequestBody QuestionEntity question) {
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/{id}")
    public QuestionDTO deleteQuestion(@PathVariable Long id) {
        return questionService.deleteQuestion(id);
    }
}
