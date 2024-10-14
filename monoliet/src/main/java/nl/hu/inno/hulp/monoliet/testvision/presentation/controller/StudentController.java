package nl.hu.inno.hulp.monoliet.testvision.presentation.controller;

import nl.hu.inno.hulp.monoliet.testvision.application.service.StudentService;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.ExtraTimeRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.StudentRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.StudentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public StudentResponse getStudent(@PathVariable Long id) {
        return new StudentResponse(studentService.getStudent(id));
    }

    @PostMapping("/add")
    public StudentResponse addStudent(@RequestBody StudentRequest studentRequest) {
        return new StudentResponse(studentService.addStudent(studentRequest));
    }

    @PatchMapping("/extratime")
    public StudentResponse changeExtraTimeRight(@RequestBody ExtraTimeRequest studentRequest) {
        return new StudentResponse(studentService.changeExtraTimeRight(studentRequest));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
