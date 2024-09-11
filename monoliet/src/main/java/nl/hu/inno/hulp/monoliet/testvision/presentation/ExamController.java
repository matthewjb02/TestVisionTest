package nl.hu.inno.hulp.monoliet.testvision.presentation;

import nl.hu.inno.hulp.monoliet.testvision.application.ExamService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/start")
    public void startExam() {
    }

    @GetMapping("/getQuestion")
    public void getQuestion() {
    }

    @PostMapping("/answer")
    public void enterAnswer() {
    }

    @PostMapping("/end")
    public void endExam() {
    }
}
