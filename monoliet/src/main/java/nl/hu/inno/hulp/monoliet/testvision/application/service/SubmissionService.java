package nl.hu.inno.hulp.monoliet.testvision.application.service;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.monoliet.testvision.data.SubmissionRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.Examination;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Grading;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import nl.hu.inno.hulp.monoliet.testvision.domain.test.Test;
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
    private final TestService testService;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository, TeacherService teacherService, TestService testService) {
        this.submissionRepository = submissionRepository;
        this.teacherService = teacherService;
        this.testService = testService;
    }

    private Test findTestById(Long testId) {
        return testService.getTest(testId);
    }

    private Submission findSubmissionByTestAndStudentId(Test test, Long studentId) {
        return test.getSubmissions().stream()
                .filter(submission -> submission.getExamination().getStudent().getId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Submission not found for the given student and test"));
    }

    public List<SubmissionResponse> getSubmissionsByTestId(Long testId) {
        Test test = findTestById(testId);
        return test.getSubmissions().stream()
                .map(submission -> new SubmissionResponse(
                        submission.getExamination(),
                        submission.getId(),
                        submission.getStatus(),
                        submission.getGrading()
                ))
                .collect(Collectors.toList());
    }

    public List<SubmissionResponse> getSubmissionsByTestAndStudentIdFromExam(Long testId, Long studentId) {
        Test test = findTestById(testId);
        return test.getSubmissions().stream()
                .filter(submission -> submission.getStudentIDtFromExamSubmission().equals(studentId))
                .map(submission -> new SubmissionResponse(
                        new Examination(submission.getStudentFromExamSubmission(), test),
                        submission.getId(),
                        submission.getStatus(),
                        submission.getGrading())).collect(Collectors.toList());
    }

    public void updateOpenQuestionGrading(Long testId, Long studentId, int questionNr, UpdateQuestionGradingRequest request) {
        Test test = findTestById(testId);
        Submission submission = findSubmissionByTestAndStudentId(test, studentId);
        submission.updateGradingForQuestion(questionNr, request.getGivenPoints(), request.getFeedback());
        submissionRepository.save(submission);
    }

    public void addGrading(Long testId, Long studentId, GradingRequest request) {
        Test test = findTestById(testId);
        Submission submission = findSubmissionByTestAndStudentId(test, studentId);
        Teacher teacher = teacherService.getTeacherById(request.getTeacherId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));

        Grading grading = Grading.createGrading(submission.calculateGrade(), request.getComments());
        grading.setGrader(teacher);
        submission.addGrading(grading);

        // after the final grade we update the test statistics
        test.updateStatistics();
        testService.saveTest(test);

        submissionRepository.save(submission);
    }
}