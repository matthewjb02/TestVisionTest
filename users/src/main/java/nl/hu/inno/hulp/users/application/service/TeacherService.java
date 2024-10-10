package nl.hu.inno.hulp.users.application.service;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.commons.request.TeacherRequest;
import nl.hu.inno.hulp.commons.response.TeacherResponse;
import nl.hu.inno.hulp.users.data.TeacherRepository;
import nl.hu.inno.hulp.users.domain.Teacher;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElseThrow();
    }

    public TeacherResponse getTeacherResponse(Long id) {
        Teacher teacher = getTeacherById(id);
        return new TeacherResponse(teacher.getId(), teacher.getFirstName(), teacher.getLastName(),
                teacher.getEmail().getEmailString());
    }

    public TeacherResponse saveTeacher(TeacherRequest teacherRequest) {
        Teacher teacher = new Teacher(teacherRequest.firstName(), teacherRequest.lastName(), teacherRequest.email());
        teacherRepository.save(teacher);
        return getTeacherResponse(teacher.getId());
    }

    public void removeTeacher(Long id ) {
        teacherRepository.deleteById(id);
    }
}