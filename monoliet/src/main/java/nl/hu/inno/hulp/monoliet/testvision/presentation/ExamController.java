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

    @GetMapping("/getQuestion/{nr}")
    public void getQuestion(@PathVariable("nr") int testNr) {
    }

    @PostMapping("/answer/{nr}")
    public void enterAnswer(@PathVariable("nr") int testNr) {
    }

    @PostMapping("/end")
    public void endExam() {
    }
}
