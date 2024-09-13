package nl.hu.inno.hulp.monoliet.presentation;

import nl.hu.inno.hulp.monoliet.application.TeacherService;
import nl.hu.inno.hulp.monoliet.testvision.domain.Teacher;
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
    public Teacher saveTeacher(@RequestBody PostTeacherDTO postTeacherDTO) {
       return this.teacherService.SaveTeacher(postTeacherDTO.getFirstName(), postTeacherDTO.getLastName(), postTeacherDTO.getEmail());
    }
    @GetMapping
    public Optional<Teacher> getTeacher(@RequestParam long id) {
        return teacherService.GetTeacherById(id);
    }
    @GetMapping("/all")
    public List<Teacher> getAllTeachers() {
        return teacherService.GetAllTeachers();
    }
    public void deleteTeacher(@RequestParam long id) {
        if (getTeacher(id).isPresent()) {
        teacherService.RemoveTeacher(getTeacher(id).get());}
    }
}
