package nl.hu.inno.hulp.monoliet.testvision.presentation.controller;

import nl.hu.inno.hulp.monoliet.testvision.application.dto.ExamDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.service.ExamService;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.GradingCriteriaDTO;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
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
    public List<ExamDTO> getAllExams() {
        return examService.getAllExams();
    }

    @GetMapping("/{id}")
    public ExamDTO getExamById(@PathVariable Long id) {
        return examService.getExamById(id);
    }

    @PostMapping("/examMaker/{makerId}/examValidator/{examValidatorId}")
    public ExamDTO addExam(@PathVariable Long courseId,@RequestBody Exam exam, @PathVariable Long makerId, @PathVariable Long examValidatorId) {
        return examService.addExam(exam, makerId, examValidatorId,courseId);
    }
    
    @DeleteMapping("/{id}")
    public ExamDTO deleteExam(@PathVariable Long id) {
        return examService.deleteExam(id);
    }

    @PostMapping("/{examId}/questions")
    public ExamDTO addQuestionsByIdsToExam(@PathVariable Long examId, @RequestBody List<Long> questionIds) {
        return examService.addQuestionsByIdsToExam(examId, questionIds);
    }

    @PostMapping("/{examId}/grading-criteria")
    public ExamDTO addGradingCriteriaToExam(@PathVariable Long examId, @RequestBody GradingCriteriaDTO gradingCriteriaDTO) {
        return examService.addGradingCriteriaToExam(examId, gradingCriteriaDTO);
    }
}
