package nl.hu.inno.hulp.monoliet.testvision.presentation.controller;

import nl.hu.inno.hulp.monoliet.testvision.application.dto.CourseDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.ExamDTO;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.application.service.CourseService;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.CourseRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.CourseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseResponse getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    public CourseResponse addCourse(@RequestBody CourseRequest course) {
        return courseService.addCourse(course);
    }

    @DeleteMapping("/{id}")
    public CourseResponse deleteCourse(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }
    @PostMapping("/{courseId}/teachers/{teacherId}")
    public CourseResponse addTeacherToCourse(@PathVariable Long courseId ,@PathVariable Long teacherId) {
       return courseService.addTeacherToCourse(courseId, teacherId);
    }
    @PostMapping("/{courseId}/exams/{examId}")
    public CourseResponse addTestToCourse(@PathVariable Long courseId, @PathVariable Long examId) {
        return courseService.addTestToCourse(courseId, examId);
    }
    @PutMapping("{id}/exams/{examId}/accept")
    public ExamDTO acceptExam(@PathVariable Long id, @PathVariable Long examId) throws Exception {
        return courseService.acceptExam(examId,id);
    }
    @PutMapping("{id}/exams/{examId}/reject")
    public ExamDTO rejectExam(@PathVariable Long id, @PathVariable Long examId,@RequestBody String reason) throws Exception {
        return courseService.rejectExam(examId,id, reason);
    }
    @GetMapping("/{id}/exams/{examId}/reject/view")
    public ExamDTO viewDeniedExam(@PathVariable Long examId,@PathVariable Long id) throws Exception {
        return courseService.viewDeniedExam(examId,id);
    }
    @PutMapping("/{id}/exams/{examId}/reject/modify")
    public ExamDTO modifyWrongExam(@PathVariable Long examId,@PathVariable Long id,@RequestBody List<Question>newQuestions) throws Exception {
        return courseService.modifyWrongExam(examId,id, newQuestions);
    }
}
