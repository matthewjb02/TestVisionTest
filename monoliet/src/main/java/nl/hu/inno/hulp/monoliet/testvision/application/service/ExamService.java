package nl.hu.inno.hulp.monoliet.testvision.application.service;

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
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.GradingCriteria;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Statistics;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {
//todo fix maker/validator assigning
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public ExamService(ExamRepository examRepository, QuestionRepository questionRepository, TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }
    public ExamDTO addExam(Exam exam, long examMakerId, long examValidatorId,long courseId) {
        Teacher  maker=teacherRepository.findById(examMakerId).orElseThrow();
        Teacher examValidator=teacherRepository.findById(examValidatorId).orElseThrow();
        Course course= courseRepository.findById(courseId).orElseThrow();
        exam.addExamValidator(examValidator);
        exam.addExamMaker(maker);
        exam.addCourse(course);

        Statistics statistics = Statistics.createStatistics(0, 0, 0, 0);
        exam.addStatistics(statistics);
        Exam savedExam = examRepository.save(exam);

        return toDTO(savedExam);
    }

    public ExamDTO deleteExam(Long id) {
        ExamDTO oldExamDTO = getExamById(id);
        examRepository.deleteById(id);
        return oldExamDTO;
    }
    public List<ExamDTO> getAllExams() {
        List<Exam> allExams = examRepository.findAll();
        List<ExamDTO> examDTOS = new ArrayList<>();
        for (Exam exam : allExams) {
            examDTOS.add(toDTO(exam));
        }

        return examDTOS;
    }
    public ExamDTO validateExams(long validatorId,long examId,long courseId) {
        Course course=courseRepository.findById(courseId).orElseThrow();
        Exam exam = examRepository.findById(examId).orElseThrow();
        Teacher teacher=teacherRepository.findById(validatorId).orElseThrow();
        if (course.getValidatingExams().contains(exam)&&course.getTeachers().contains(teacher)) {
        return toDTO(exam);}
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    public ExamDTO acceptExam(long examId) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        exam.approveExam();
        examRepository.save(exam);
        return toDTO(exam);
    }
    public ExamDTO rejectExam(long examId, String reason) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        exam.rejectExam(reason);
        examRepository.save(exam);
        return toDTO(exam);
    }

    public ExamDTO viewDeniedExam(long examId) throws Exception {

        Exam exam = examRepository.findById(examId).orElseThrow();
        exam.viewWrongExam();
        return toDTO(exam);
    }
    public ExamDTO modifyWrongExam(long examId, List<Question>newQuestions) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();

        exam.modifyQuestions(exam.getQuestions(),newQuestions);

        questionRepository.saveAll(exam.getQuestions());
        examRepository.save(exam);
        return toDTO(exam);
    }
    public ExamDTO getExamById(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No exam with id: " + id + " found!"));

        return toDTO(exam);
    }

    public Exam getExam(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No exam with id: " + id + " found!"));
        return exam;
    }



    public ExamDTO addQuestionsByIdsToExam(Long examId, List<Long> questionIds) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam not found"));
        List<Question> newQuestions = questionRepository.findAllById(questionIds);
        exam.getQuestions().addAll(newQuestions);
        exam.calculateTotalPoints();
        examRepository.save(exam);
        return toDTO(exam);
    }

    public ExamDTO addGradingCriteriaToExam(Long examId, GradingCriteriaDTO gradingCriteriaDTO) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam not found"));

        GradingCriteria gradingCriteria = new GradingCriteria(
                gradingCriteriaDTO.openQuestionWeight(),
                gradingCriteriaDTO.closedQuestionWeight()
        );

        exam.addGradingCriteria(gradingCriteria);
        examRepository.save(exam);

        return toDTO(exam);
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

       private List<QuestionDTO> getQuestionDTOs(List<Question> questions) {

        if (questions == null){
            return null;
        }

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



    public void saveExam(Exam exam) {
        examRepository.save(exam);
    }

}
