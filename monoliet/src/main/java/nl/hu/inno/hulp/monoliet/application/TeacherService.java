package nl.hu.inno.hulp.monoliet.application;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.monoliet.presentation.PostTeacherDTO;
import nl.hu.inno.hulp.monoliet.persistence.TeacherRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;

    public TeacherService() {
    }

    public Teacher SaveTeacher(String firstName, String lastName, String email) {
        Teacher teacher = new Teacher(firstName, lastName, email);
         teacherRepository.save(teacher);
         return teacher;
    }
    public Optional<Teacher> GetTeacherById(long id) {
        return teacherRepository.findById(id);
    }
    public List<Teacher> GetAllTeachers() {
        return teacherRepository.findAll();
    }
    public void RemoveTeacher(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

}
