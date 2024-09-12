package nl.hu.inno.hulp.monoliet.application;

import nl.hu.inno.hulp.monoliet.persistence.TeacherRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;

    public Teacher SaveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
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
