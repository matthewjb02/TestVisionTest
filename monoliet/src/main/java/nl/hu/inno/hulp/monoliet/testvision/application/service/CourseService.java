package nl.hu.inno.hulp.monoliet.testvision.application.service;

import nl.hu.inno.hulp.commons.messaging.CourseDTO;
import nl.hu.inno.hulp.commons.messaging.TeacherDTO;
import nl.hu.inno.hulp.monoliet.testvision.data.CourseRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.ExamRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.QuestionRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.CourseRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;
    private final TeacherService teacherService;
    private final QuestionRepository questionRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, ExamRepository examRepository, TeacherService teacherService,  QuestionRepository questionRepository) {
        this.courseRepository = courseRepository;
        this.examRepository = examRepository;
        this.teacherService = teacherService;
        this.questionRepository = questionRepository;
    }

    public List<CourseResponse> getAllCourses() {
        List<Course> allCourses = courseRepository.findAll();
        List<CourseResponse> courseDTOs = new ArrayList<>();
        for (Course course : allCourses){
            courseDTOs.add(getDTO(course));
        }

        return courseDTOs;
    }

    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course with id: " + id + " found!"));

        return getDTO(course);
    }

    public CourseResponse addCourse(CourseRequest course) {
        Course savedCourse = new Course(course.name());
        courseRepository.save(savedCourse);
        return getDTO(savedCourse);
    }

    public CourseResponse deleteCourse(Long id) {
        CourseResponse oldDTO = getCourseById(id);
        courseRepository.deleteById(id);
        return oldDTO;
    }
    public CourseResponse addTeacherToCourse(Long courseId, Long teacherId) {
        Course course=courseRepository.findById(courseId).orElseThrow();
        Teacher teacher = teacherService.findById(teacherId);
        course.addTeacher(teacher);
        courseRepository.save(course);
        return getDTO(course);
    }

    public CourseResponse addTestToCourse(Long courseId, Long testId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        Exam exam = examRepository.findById(testId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test not found"));

        course.addExam(exam);
        courseRepository.save(course);

        return getDTO(course);
    }

    public List<ExamResponse> getAllTestsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course with id: " + courseId + " found!"));

        List<ExamResponse> examDTOS = new ArrayList<>();
        for (Exam exam : course.getApprovedExams()) {
            examDTOS.add(new ExamResponse(exam));
        }

        return examDTOS;
    }

    public ExamResponse acceptExam(long examId, Long courseId) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();

        course.approveExam(exam,exam.getExamValidator());
        examRepository.save(exam);
        return new ExamResponse(exam);
    }
    public ExamResponse rejectExam(long examId, Long courseId, String reason) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.rejectExam(exam,exam.getExamValidator(),reason);
        examRepository.save(exam);
        return new ExamResponse(exam);
    }

    public ExamResponse viewDeniedExam(long examId, Long courseId) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.viewWrongExam(exam);
        return new ExamResponse(exam);
    }
  
    public ExamResponse modifyWrongExam(long examId, Long courseId, List<QuestionEntity>newQuestions) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.modifyQuestions(exam,exam.getQuestions(),newQuestions);

        questionRepository.saveAll(exam.getQuestions());
        examRepository.save(exam);
        return new ExamResponse(exam);
    }
    private TeacherResponse getTeacherDTO(Teacher teacher) {
        return new TeacherResponse(
                teacher);
    }

    private CourseResponse getDTO(Course course){
        List<ExamResponse> approvedExamDTOS = new ArrayList<>();
        for (Exam exam : course.getApprovedExams()){
            approvedExamDTOS.add(new ExamResponse(exam));
        }
        List<ExamResponse> rejectedExamDTOS = new ArrayList<>();
        for (Exam exam : course.getRejectedExams()){
            rejectedExamDTOS.add(new ExamResponse(exam));
        }
        List<ExamResponse> validatingExamDTOS = new ArrayList<>();
        for (Exam exam : course.getValidatingExams()){
            validatingExamDTOS.add(new ExamResponse(exam));
        }
        List<TeacherResponse> teacherResponses = new ArrayList<>();
        for (Teacher teacher : course.getTeachers()){
            teacherResponses.add(getTeacherDTO(teacher));
        }
      
        return new CourseResponse( course);
    }

    private List<QuestionResponse> getQuestionResponses(List<QuestionEntity> questions) {
        List<QuestionResponse> responses = new ArrayList<>();

        for (QuestionEntity question : questions){
            if (question.getClass().equals(MultipleChoiceQuestion.class)){
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;

                responses.add(new MultipleChoiceQuestionResponse(mcQuestion));
            } else {
                OpenQuestion openQuestion = (OpenQuestion)question;

                responses.add(new OpenQuestionResponse(openQuestion));
            }
        }
        return responses;
    }

    public Course findCourseByExamId(Long examId) {
        return courseRepository.findByApprovedExamsId(examId);
    }

    // Messaging
    public CourseDTO mFindCourseByExamId(Long examId) {
        Course course = courseRepository.findByApprovedExamsId(examId);
        List<TeacherDTO> teacherDTOs = new ArrayList<>();

        for (Teacher teacher : course.getTeachers()) {
            teacherDTOs.add(new TeacherDTO(teacher.getId()));
        }

        return new CourseDTO(course.getId(), teacherDTOs);


    }

}
