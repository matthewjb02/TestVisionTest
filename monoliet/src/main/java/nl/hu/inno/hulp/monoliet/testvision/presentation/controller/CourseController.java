package nl.hu.inno.hulp.monoliet.testvision.presentation.controller;

import nl.hu.inno.hulp.monoliet.testvision.application.dto.CourseDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.ExamDTO;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.application.service.CourseService;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
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
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseDTO getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    public CourseDTO addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @DeleteMapping("/{id}")
    public CourseDTO deleteCourse(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }
    @PostMapping("/{courseId}/teachers/{teacherId}")
    public CourseDTO addTeacherToCourse(@PathVariable Long courseId ,@PathVariable Long teacherId) {
       return courseService.addTeacherToCourse(courseId, teacherId);
    }
    @PostMapping("/{courseId}/exams/{examId}")
    public CourseDTO addTestToCourse(@PathVariable Long courseId, @PathVariable Long examId) {
        return courseService.addTestToCourse(courseId, examId);
    }
}
