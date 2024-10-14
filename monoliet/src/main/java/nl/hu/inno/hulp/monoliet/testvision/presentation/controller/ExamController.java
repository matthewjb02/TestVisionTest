package nl.hu.inno.hulp.monoliet.testvision.presentation.controller;

import nl.hu.inno.hulp.monoliet.testvision.application.service.ExamService;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.GradingCriteriaDTO;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.ExamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/exams")
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public List<ExamResponse> getAllExams() {
        return examService.getAllExams();
    }

    @GetMapping("/{id}")
    public ExamResponse getExamById(@PathVariable Long id) {
        return examService.getExamById(id);
    }

    @PostMapping("/examMaker/{makerId}/examValidator/{examValidatorId}")
    public ExamResponse addExam( @PathVariable Long makerId, @PathVariable Long examValidatorId) {
        return examService.addExam(makerId, examValidatorId);
    }
    
    @DeleteMapping("/{id}")
    public ExamResponse deleteExam(@PathVariable Long id) {
        return examService.deleteExam(id);
    }

    @PostMapping("/{examId}/questions")
    public ExamResponse addQuestionsByIdsToExam(@PathVariable Long examId, @RequestBody List<Long> questionIds) {
        return examService.addQuestionsByIdsToExam(examId, questionIds);
    }

    @PostMapping("/{examId}/grading-criteria")
    public ExamResponse addGradingCriteriaToExam(@PathVariable Long examId, @RequestBody GradingCriteriaDTO gradingCriteriaDTO) {
        return examService.addGradingCriteriaToExam(examId, gradingCriteriaDTO);
    }
}
