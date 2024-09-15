package nl.hu.inno.hulp.monoliet.testvision.presentation;

import nl.hu.inno.hulp.monoliet.testvision.application.TeacherDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.TeacherService;
import nl.hu.inno.hulp.monoliet.testvision.application.TestDTO;
import nl.hu.inno.hulp.monoliet.testvision.domain.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDTO saveTeacher(@RequestBody Teacher teacher) {
       return this.teacherService.saveTeacher(teacher);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<Teacher> getTeacher(@PathVariable long id) {
        return teacherService.getTeacherById(id);
    }
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTeacher(@PathVariable long id) {
        teacherService.removeTeacher(id);}
    @PostMapping("/{teacherId}/courses/{courseId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TeacherDTO addCourseToTeacher(@PathVariable long teacherId, @PathVariable long courseId) {
        return this.teacherService.addCourseToTeacher(teacherId, courseId);
    }
    @GetMapping("/{teacherId}/courses/{courseId}/tests/{testId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TestDTO validateTest(@PathVariable long teacherId, @PathVariable long courseId, @PathVariable long testId) throws Exception {
        return this.teacherService.validateTests(teacherId,courseId,testId);
    }
    @PatchMapping("/{teacherId}/courses/{courseId}/tests/{testId}/accept")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TestDTO acceptTest(@PathVariable long teacherId, @PathVariable long courseId, @PathVariable long testId) throws Exception {
        return this.teacherService.acceptTest(testId,teacherId,courseId);
    }
    @PatchMapping("/{teacherId}/courses/{courseId}/tests/{testId}/refuse")
    @ResponseStatus(HttpStatus.OK)
    public TestDTO refuseTest(@PathVariable long teacherId, @PathVariable long courseId, @PathVariable long testId, @RequestBody String reason) throws Exception {
        return  this.teacherService.refuseTest(testId,teacherId,courseId,reason);
    }
    @GetMapping("/{teacherId}/courses/{courseId}/tests/{testId}/refuse/modify")
    @ResponseStatus(HttpStatus.OK)
    public TestDTO viewWrongTests(@PathVariable long testId,@PathVariable long teacherId,@PathVariable long courseId) throws Exception {
        return  this.teacherService.viewWrongTest(testId,teacherId,courseId);
    }
    @PutMapping("/{teacherId}/courses/{courseId}/tests/{testId}/refuse/modify")
    @ResponseStatus(HttpStatus.OK)
    public TestDTO modifyWrongTest(@PathVariable long testId,@PathVariable long teacherId, @PathVariable long courseId, @RequestBody List<Question>newQuestions) throws Exception {
        return this.teacherService.modifyWrongTest(testId,teacherId,courseId, newQuestions);
    }
}

