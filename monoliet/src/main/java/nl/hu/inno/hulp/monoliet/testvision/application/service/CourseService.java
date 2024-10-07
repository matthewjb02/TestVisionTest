package nl.hu.inno.hulp.monoliet.testvision.application.service;

import nl.hu.inno.hulp.monoliet.testvision.application.dto.*;
import nl.hu.inno.hulp.monoliet.testvision.data.ExamRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.QuestionRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.TeacherRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.data.CourseRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.QuestionRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.ExamResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.MultipleChoiceQuestionResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.OpenQuestionResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.QuestionResponse;
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
    private final ExamRepository examRepository;
    private final TeacherRepository teacherRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, ExamRepository examRepository, TeacherRepository teacherRepository, QuestionRepository questionRepository) {
        this.courseRepository = courseRepository;
        this.examRepository = examRepository;
        this.teacherRepository = teacherRepository;
        this.questionRepository = questionRepository;
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
    public CourseDTO addTeacherToCourse(Long courseId, Long teacherId) {
        Course course=courseRepository.findById(courseId).orElseThrow();
        Teacher teacher= teacherRepository.findById(teacherId).orElseThrow();
        course.addTeacher(teacher);
        courseRepository.save(course);
        return getDTO(course);
    }

    public CourseDTO addTestToCourse(Long courseId, Long testId) {
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

        course.approveExam(exam,exam.getExamValidatorMail());
        examRepository.save(exam);
        return new ExamResponse(exam);
    }
    public ExamResponse rejectExam(long examId, Long courseId, String reason) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.rejectExam(exam,exam.getExamValidatorMail(),reason);
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
    private TeacherDTO getTeacherDTO(Teacher teacher) {
        return new TeacherDTO(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail().getEmailString());
    }
    private CourseDTO getDTO(Course course){
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
        List<TeacherDTO> teacherDTOS = new ArrayList<>();
        for (Teacher teacher : course.getTeachers()){
            teacherDTOS.add(getTeacherDTO(teacher));
        }


        return new CourseDTO(
                course.getId(),
                course.getName(),
                teacherDTOS,
                approvedExamDTOS,
                rejectedExamDTOS,
                validatingExamDTOS

        );
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

}
