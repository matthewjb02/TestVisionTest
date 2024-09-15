package nl.hu.inno.hulp.monoliet.testvision.application;

import nl.hu.inno.hulp.monoliet.testvision.data.StudentRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.StudentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Transactional
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No student with id: " + id + " found!"));
    }

    public Student addStudent(StudentRequest studentRequest) {
        Student student = new Student(studentRequest.voornaam, studentRequest.achternaam);
        studentRepository.save(student);
        return getStudent(student.getId());
    }

    public void deleteStudent(Long id) {
        studentRepository.delete(getStudent(id));
    }
}
