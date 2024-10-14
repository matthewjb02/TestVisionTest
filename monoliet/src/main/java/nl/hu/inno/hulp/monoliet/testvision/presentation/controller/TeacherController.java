package nl.hu.inno.hulp.monoliet.testvision.presentation.controller;

import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.TeacherResponse;
import nl.hu.inno.hulp.monoliet.testvision.application.service.TeacherService;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.TeacherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public TeacherResponse saveTeacher(@RequestBody TeacherRequest teacher) {
       return new TeacherResponse(this.teacherService.saveTeacher(teacher));
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public TeacherResponse getTeacher(@PathVariable long id) {
        return new TeacherResponse(teacherService.getTeacherById(id));
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

}

