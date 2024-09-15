package nl.hu.inno.hulp.monoliet.testvision.application;

import nl.hu.inno.hulp.monoliet.testvision.application.dto.GradingCriteriaDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.StatisticsDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.SubmissionDTO;
import nl.hu.inno.hulp.monoliet.testvision.data.TestRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.data.CourseRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TestRepository testRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, TestRepository testRepository) {
        this.courseRepository = courseRepository;
        this.testRepository = testRepository;
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

    public CourseDTO addTestToCourse(Long courseId, Long testId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test not found"));

        course.addTest(test);
        courseRepository.save(course);

        return getDTO(course);
    }

    public List<TestDTO> getAllTestsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course with id: " + courseId + " found!"));

        List<TestDTO> testDTOs = new ArrayList<>();
        for (Test test : course.getApprovedTests()) {
            testDTOs.add(getTestDTO(test));
        }

        return testDTOs;
    }

    private CourseDTO getDTO(Course course){
        List<TestDTO> approvedTestDTOs = new ArrayList<>();
        for (Test test : course.getApprovedTests()){
            approvedTestDTOs.add(getTestDTO(test));
        }
        List<TestDTO>rejectedTestDTOs = new ArrayList<>();
        for (Test test : course.getRejectedTests()){
            rejectedTestDTOs.add(getTestDTO(test));
        }
        List<TestDTO> validatingTestDTOs = new ArrayList<>();
        for (Test test : course.getValidatingTests()){
            validatingTestDTOs.add(getTestDTO(test));
        }


        return new CourseDTO(
                course.getId(),
                course.getName(),
                approvedTestDTOs,
                rejectedTestDTOs,
                validatingTestDTOs

        );
    }


    private TestDTO getTestDTO(Test test) {
        GradingCriteriaDTO gradingCriteriaDTO = null;
        if (test.getGradingCriteria() != null) {
            gradingCriteriaDTO = new GradingCriteriaDTO(
                    test.getGradingCriteria().getOpenQuestionWeight(),
                    test.getGradingCriteria().getClosedQuestionWeight()
            );
        }

        List<SubmissionDTO> submissionDTOs = test.getSubmissions().stream()
                .map(submission -> new SubmissionDTO(submission.getId(), submission.getStatus()))
                .collect(Collectors.toList());

        StatisticsDTO statisticsDTO = null;
        if (test.getStatistics() != null) {
            statisticsDTO = new StatisticsDTO(
                    test.getStatistics().getId(),
                    test.getStatistics().getSubmissionCount(),
                    test.getStatistics().getPassCount(),
                    test.getStatistics().getFailCount(),
                    test.getStatistics().getAverageScore()
            );
        }

      return new TestDTO(
                test.getId(),
                test.getTotalPoints(),
                test.getQuestions(),
                test.getTotalPoints(),
                test.getMakerMail(),
                test.getTestValidatorMail(),
                test.getValidationStatus(),
                test.getReason(),
                gradingCriteriaDTO,
                submissionDTOs,
                statisticsDTO
        );
    }

}
