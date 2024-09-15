package nl.hu.inno.hulp.monoliet.testvision.presentation;

import nl.hu.inno.hulp.monoliet.testvision.application.StudentService;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.StudentRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.StudentResponse;
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

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
