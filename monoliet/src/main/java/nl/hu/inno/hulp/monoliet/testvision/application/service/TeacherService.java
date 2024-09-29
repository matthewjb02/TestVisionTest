package nl.hu.inno.hulp.monoliet.testvision.application.service;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.*;
import nl.hu.inno.hulp.monoliet.testvision.data.CourseRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.QuestionRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.TeacherRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.ExamRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    ExamRepository examRepository;
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
    public CourseDTO getCourseByTeacher(long teacherId, long courseId) {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow();
        Course course=courseRepository.findById(courseId).orElseThrow();
        Course teachersCourse=null;
        if (teacher.getCourses().contains(course)) {
            teachersCourse=course;
        }
        else throw new IllegalArgumentException("teacher doesnt teach course not found");
        return getCourseDTO(teachersCourse);
    }
    public long getCourseId(long teacherId, long examId) {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow();
        Exam exam=examRepository.findById(examId).orElseThrow();
        long courseId=-1;
        for (Course course:teacher.getCourses()) {
            if(course.getValidatingExams().contains(exam)||course.getApprovedExams().contains(exam)||course.getRejectedExams().contains(exam)) {
               courseId=course.getId();
            }
        }
        if (courseId==-1) {
            throw new IllegalArgumentException("Course not found");
        }
        return courseId;
    }
    public ExamDTO validateExams(long teacherId, long examId) throws Exception {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow();
        Exam exam = examRepository.findById(examId).orElseThrow();
        teacher.validateOtherExams(exam);
        return getExamDTO(exam);
    }
    public ExamDTO acceptExam(long examId, long teacherId) throws Exception {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow();
        Exam exam = examRepository.findById(examId).orElseThrow();
        teacher.approveExam(exam);
        examRepository.save(exam);
        return getExamDTO(exam);
    }
    public ExamDTO refuseExam(long examId, long teacherId, String reason) throws Exception {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow();
        Exam exam = examRepository.findById(examId).orElseThrow();
        teacher.rejectExam(exam, reason);
       examRepository.save(exam);
        return getExamDTO(exam);
    }
    public ExamDTO viewWrongExam(long examId, long teacherId) throws Exception {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow();

        Exam exam = examRepository.findById(examId).orElseThrow();
        teacher.viewWrongExam(exam);
        return getExamDTO(exam);
    }
    public ExamDTO modifyWrongExam(long examId, long teacherId, List<Question>newQuestions) throws Exception {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow();
        Exam exam = examRepository.findById(examId).orElseThrow();

        teacher.modifyQuestions(exam, exam.getQuestions(),newQuestions);
        System.out.println(exam.getQuestionsAsString());

        questionRepository.saveAll(exam.getQuestions());
        examRepository.save(exam);
        Exam exam1 = examRepository.findById(examId).orElseThrow();
        System.out.println(exam1.getQuestionsAsString());
        return getExamDTO(exam);
    }

    private ExamDTO getExamDTO(Exam exam) {
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

    private CourseDTO getCourseDTO(Course course){
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


        return new CourseDTO(
                course.getId(),
                course.getName(),
                approvedExamDTOS,
                rejectedExamDTOS,
                validatingExamDTOS

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
    private List<QuestionDTO> getQuestionDTOs(List<Question> questions) {
        List<QuestionDTO> dtos = new ArrayList<>();

        for (Question question : questions){
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


}
