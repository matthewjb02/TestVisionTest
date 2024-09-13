package nl.hu.inno.hulp.monoliet.testvision.application;

import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.data.CourseRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseDTO> getAllCourses() {
        List<Course> allCourses = courseRepository.findAll();
        List<CourseDTO> courseDTOs = new ArrayList<>();
        for (Course course : allCourses){
            courseDTOs.add(getDTO(course));
        }

        return courseDTOs;
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course with id: " + id + " found!"));

        return getDTO(course);
    }

    public CourseDTO addCourse(Course course) {
        Course savedCourse = courseRepository.save(course);
        return getDTO(savedCourse);
    }

    public CourseDTO deleteCourse(Long id) {
        CourseDTO oldDTO = getCourseById(id);
        courseRepository.deleteById(id);
        return oldDTO;
    }

    private CourseDTO getDTO(Course course){
        List<TestDTO> testDTOs = new ArrayList<>();
        for (Test test : course.getTests()){
            testDTOs.add(getTestDTO(test));
        }

        return new CourseDTO(
                course.getId(),
                course.getName(),
                testDTOs
        );
    }

    private TestDTO getTestDTO(Test test){
        return new TestDTO(
                test.getId(),
                test.getQuestionsAsString(),
                test.getTotalPoints()
        );
    }
}
