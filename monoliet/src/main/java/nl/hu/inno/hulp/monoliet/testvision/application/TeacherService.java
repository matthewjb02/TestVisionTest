package nl.hu.inno.hulp.monoliet.testvision.application;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.monoliet.testvision.data.CourseRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.TeacherRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.TestRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.domain.Teacher;
import nl.hu.inno.hulp.monoliet.testvision.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TestRepository testRepository;

    public TeacherService() {
    }

    public TeacherDTO saveTeacher(Teacher teacher) {

         teacherRepository.save(teacher);
         return new TeacherDTO(teacher.getId(),teacher.getFirstName(),teacher.getLastName(),teacher.getEmail().getEmail(),teacher.getCourses());
    }
    public Optional<Teacher> getTeacherById(long id) {
        return teacherRepository.findById(id);
    }
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
    public void removeTeacher(long id ) {
        teacherRepository.deleteById(id);
    }
    public TeacherDTO addCourseToTeacher(long teacherId, long courseId) {
        Teacher teacher=new Teacher();
        Course course=new Course();
        if (teacherRepository.findById(teacherId).isPresent()&&courseRepository.findById(courseId).isPresent()) {
        teacher = teacherRepository.findById(teacherId).get();
        course = courseRepository.findById(courseId).get();}
        else{
            throw new RuntimeException("Course or Teacher not found");
        }
        teacher.addCourse(course);
        teacherRepository.save(teacher);
        return new TeacherDTO(teacher.getId(),teacher.getFirstName(),teacher.getLastName(),teacher.getEmail().getEmail(),teacher.getCourses());
    }



}
