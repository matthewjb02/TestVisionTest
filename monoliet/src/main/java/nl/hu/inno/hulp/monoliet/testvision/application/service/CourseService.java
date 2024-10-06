package nl.hu.inno.hulp.monoliet.testvision.application.service;

import nl.hu.inno.hulp.monoliet.testvision.application.dto.*;
import nl.hu.inno.hulp.monoliet.testvision.data.ExamRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.QuestionRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.TeacherRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.data.CourseRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
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

    public List<ExamDTO> getAllTestsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course with id: " + courseId + " found!"));

        List<ExamDTO> examDTOS = new ArrayList<>();
        for (Exam exam : course.getApprovedExams()) {
            examDTOS.add(getTestDTO(exam));
        }

        return examDTOS;
    }

    public ExamDTO acceptExam(long examId, Long courseId) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();

        course.approveExam(exam,exam.getExamValidatorMail());
        examRepository.save(exam);
        return toDTO(exam);
    }
    public ExamDTO rejectExam(long examId, Long courseId, String reason) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.rejectExam(exam,exam.getExamValidatorMail(),reason);
        examRepository.save(exam);
        return toDTO(exam);
    }

    public ExamDTO viewDeniedExam(long examId, Long courseId) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.viewWrongExam(exam);
        return toDTO(exam);
    }
    public ExamDTO modifyWrongExam(long examId, Long courseId, List<QuestionEntity>newQuestions) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.modifyQuestions(exam,exam.getQuestions(),newQuestions);

        questionRepository.saveAll(exam.getQuestions());
        examRepository.save(exam);
        return toDTO(exam);
    }
    private TeacherDTO getTeacherDTO(Teacher teacher) {
        return new TeacherDTO(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail().getEmailString());
    }
    private CourseDTO getDTO(Course course){
        List<ExamDTO> approvedExamDTOS = new ArrayList<>();
        for (Exam exam : course.getApprovedExams()){
            approvedExamDTOS.add(getTestDTO(exam));
        }
        List<ExamDTO> rejectedExamDTOS = new ArrayList<>();
        for (Exam exam : course.getRejectedExams()){
            rejectedExamDTOS.add(getTestDTO(exam));
        }
        List<ExamDTO> validatingExamDTOS = new ArrayList<>();
        for (Exam exam : course.getValidatingExams()){
            validatingExamDTOS.add(getTestDTO(exam));
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
    private ExamDTO toDTO(Exam exam) {

        GradingCriteriaDTO gradingCriteriaDTO = new  GradingCriteriaDTO(0,0);
        if (exam.getGradingCriteria() != null) {
            gradingCriteriaDTO = new GradingCriteriaDTO(
                    exam.getGradingCriteria().getOpenQuestionWeight(),
                    exam.getGradingCriteria().getClosedQuestionWeight()
            );
        }

        List<SubmissionDTO> submissionDTOs = exam.getSubmissions().stream()
                .map(submission -> new SubmissionDTO(submission.getId(), submission.getStatus()))
                .collect(Collectors.toList());


        StatisticsDTO statisticsDTO = new StatisticsDTO(0, 0, 0, 0);
        if (exam.getStatistics() != null) {
            statisticsDTO = new StatisticsDTO(
                    exam.getStatistics().getSubmissionCount(),
                    exam.getStatistics().getPassCount(),
                    exam.getStatistics().getFailCount(),
                    exam.getStatistics().getAverageScore()
            );
        }


        return new ExamDTO(
                exam.getId(),
                getQuestionDTOs(exam.getQuestions()),
                exam.getTotalPoints(),
                exam.getMakerMail(),
                exam.getExamValidatorMail(),
                exam.getValidationStatus(),
                exam.getReason(),
                gradingCriteriaDTO,
                submissionDTOs,
                statisticsDTO

        );
    }

    private ExamDTO getTestDTO(Exam exam) {
        GradingCriteriaDTO gradingCriteriaDTO = new GradingCriteriaDTO(0, 0);
        if (exam.getGradingCriteria() != null) {
            gradingCriteriaDTO = new GradingCriteriaDTO(
                    exam.getGradingCriteria().getOpenQuestionWeight(),
                    exam.getGradingCriteria().getClosedQuestionWeight()
            );
        }

        List<SubmissionDTO> submissionDTOs = exam.getSubmissions().stream()
                .map(submission -> new SubmissionDTO(submission.getId(), submission.getStatus()))
                .collect(Collectors.toList());

        StatisticsDTO statisticsDTO = new StatisticsDTO(0, 0, 0, 0);
        if (exam.getStatistics() != null) {
            statisticsDTO = new StatisticsDTO(
                    exam.getStatistics().getSubmissionCount(),
                    exam.getStatistics().getPassCount(),
                    exam.getStatistics().getFailCount(),
                    exam.getStatistics().getAverageScore()
            );
        }

      return new ExamDTO(
                exam.getId(),
                getQuestionDTOs(exam.getQuestions()),
                exam.getTotalPoints(),
                exam.getMakerMail(),
                exam.getExamValidatorMail(),
                exam.getValidationStatus(),
                exam.getReason(),
                gradingCriteriaDTO,
                submissionDTOs,
                statisticsDTO
        );
    }

    private List<QuestionDTO> getQuestionDTOs(List<QuestionEntity> questions) {
        List<QuestionDTO> dtos = new ArrayList<>();

        for (QuestionEntity question : questions){
            if (question.getClass().equals(MultipleChoiceQuestion.class)){
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;

                dtos.add(new MultipleChoiceQuestionDTO(
                        mcQuestion.getId(),
                        mcQuestion.getPoints(),
                        mcQuestion.getQuestion(),
                        mcQuestion.getGivenPoints(),
                        mcQuestion.getAnswers(),
                        mcQuestion.getCorrectAnswerIndex(),
                        mcQuestion.getAnswer()));
            } else {
                OpenQuestion openQuestion = (OpenQuestion)question;

                dtos.add(new OpenQuestionDTO(
                        openQuestion.getId(),
                        openQuestion.getPoints(),
                        openQuestion.getQuestion(),
                        openQuestion.getGivenPoints(),
                        openQuestion.getTeacherFeedback(),
                        openQuestion.getCorrectAnswer(),
                        openQuestion.getAnswer()));
            }
        }
        return dtos;
    }

    public Course findCourseByExamId(Long examId) {
        return courseRepository.findByApprovedExamsId(examId);
    }

}
