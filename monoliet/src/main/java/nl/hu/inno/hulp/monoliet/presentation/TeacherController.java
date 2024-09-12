package nl.hu.inno.hulp.monoliet.presentation;

import nl.hu.inno.hulp.monoliet.application.TeacherService;
import nl.hu.inno.hulp.monoliet.testvision.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public Teacher saveTeacher(@RequestBody Teacher teacher) {
       return teacherService.SaveTeacher(teacher);
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
