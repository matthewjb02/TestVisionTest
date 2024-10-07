package nl.hu.inno.hulp.monoliet.testvision.application.service;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.monoliet.testvision.data.SubmissionRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Grading;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.GradingRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.UpdateQuestionGradingRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.SubmissionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final TeacherService teacherService;
    private final ExamService examService;
    private final CourseService courseService;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository, TeacherService teacherService, ExamService examService, CourseService courseService) {
        this.submissionRepository = submissionRepository;
        this.teacherService = teacherService;
        this.examService = examService;
        this.courseService = courseService;
    }

    private Exam findExamById(Long examId) {
        return examService.getExam(examId);
    }

    private Submission findSubmissionByExamAndStudentId(Exam exam, Long studentId) {
        return exam.getSubmissions().stream()
                .filter(submission -> submission.getExamSession().getStudent().getId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Submission not found for the given student and exam"));
    }

    public List<SubmissionResponse> getSubmissionsByExamId(Long examId) {
        Exam exam = findExamById(examId);
        return exam.getSubmissions().stream()
                .map(submission -> new SubmissionResponse(
                        submission.getExamSession(),
                        submission.getId(),
                        submission.getStatus(),
                        submission.getGrading()
                ))
                .collect(Collectors.toList());
    }


    public void updateOpenQuestionGrading(Long examId, Long studentId, int questionNr, UpdateQuestionGradingRequest request) {
        Exam exam = findExamById(examId);
        Submission submission = findSubmissionByExamAndStudentId(exam, studentId);
        submission.updateGradingForQuestion(questionNr, request.getGivenPoints(), request.getFeedback());
        submissionRepository.save(submission);
    }

    public void addGrading(Long examId, Long studentId, GradingRequest request) {
        Exam exam = findExamById(examId);
        Submission submission = findSubmissionByExamAndStudentId(exam, studentId);
        Teacher teacher = teacherService.getTeacherById(request.getTeacherId());

        // teacher can only grade the submission if the teacher teaches the course
        Course examCourse = courseService.findCourseByExamId(examId);
        if (!examCourse.getTeachers().contains(teacher)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Teacher is not allowed to grade this submission because he does not teach the course");
        }


        Grading grading = Grading.createGrading(submission.calculateGrade(), request.getComments(), teacher);
        grading.setGrader(teacher);
        submission.addGrading(grading);

        // after the final grade we update the exam statistics
        exam.updateStatistics();
        examService.saveExam(exam);

        submissionRepository.save(submission);
    }
}