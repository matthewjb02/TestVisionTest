package nl.hu.inno.hulp.monoliet.testvision.application.service;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.*;
import nl.hu.inno.hulp.monoliet.testvision.data.CourseRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.QuestionRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.TeacherRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.TestRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.test.Test;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TestRepository testRepository;
    @Autowired
    QuestionRepository questionRepository;

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
    public TestDTO validateTests(long teacherId, long courseId, long testId) throws Exception {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow();
        Course course=courseRepository.findById(courseId).orElseThrow();
        Test test=testRepository.findById(testId).orElseThrow();
        teacher.validateOtherTests(course,test);
        return getTestDTO(test);
    }
    public TestDTO acceptTest(long testId, long teacherId, long courseId) throws Exception {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow();
        Course course=courseRepository.findById(courseId).orElseThrow();
        Test test=testRepository.findById(testId).orElseThrow();
        teacher.approveTest(course,test);
        testRepository.save(test);
        return getTestDTO(test);
    }
    public TestDTO refuseTest(long testId, long teacherId, long courseId,String reason) throws Exception {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow();
        Course course=courseRepository.findById(courseId).orElseThrow();
        Test test=testRepository.findById(testId).orElseThrow();
        teacher.rejectTest(course, test, reason);
       testRepository.save(test);
        return getTestDTO(test);
    }
    public TestDTO viewWrongTest(long testId, long teacherId, long courseId) throws Exception {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow();
        Course course=courseRepository.findById(courseId).orElseThrow();
        Test test=testRepository.findById(testId).orElseThrow();
        teacher.viewWrongTest(course,test);
        return getTestDTO(test);
    }
    public TestDTO modifyWrongTest(long testId, long teacherId, long courseId, List<Question>newQuestions) throws Exception {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow();
        Course course=courseRepository.findById(courseId).orElseThrow();
        Test test=testRepository.findById(testId).orElseThrow();

        teacher.modifyQuestions(course,test,test.getQuestions(),newQuestions);
        System.out.println(test.getQuestionsAsString());

        questionRepository.saveAll(test.getQuestions());
        testRepository.save(test);
        Test test1=testRepository.findById(testId).orElseThrow();
        System.out.println(test1.getQuestionsAsString());
        return getTestDTO(test);
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
