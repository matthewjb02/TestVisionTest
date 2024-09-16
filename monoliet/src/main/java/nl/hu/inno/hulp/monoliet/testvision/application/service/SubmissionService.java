package nl.hu.inno.hulp.monoliet.testvision.application.service;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.monoliet.testvision.data.SubmissionRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.TestRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Grading;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import nl.hu.inno.hulp.monoliet.testvision.domain.test.Test;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.GradingRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.UpdateQuestionGradingRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.SubmissionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class SubmissionService {

    private final TestRepository testRepository;
    private final SubmissionRepository submissionRepository;

    @Autowired
    public SubmissionService(TestRepository testRepository, SubmissionRepository submissionRepository) {
        this.testRepository = testRepository;
        this.submissionRepository = submissionRepository;
    }


    public List<SubmissionResponse> getSubmissionsByTestId(Long testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test not found"));

        return test.getSubmissions().stream()
                .map(submission -> new SubmissionResponse(
                        submission.getExam(),
                        submission.getId(),
                        submission.getStatus(),
                        submission.getGrading()
                ))
                .collect(Collectors.toList());
    }

    public List<SubmissionResponse> getSubmissionsByTestAndStudentIdFromExam(Long testId, Long studentId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test not found"));

        return test.getSubmissions().stream().filter(submission -> submission.getExam().getStudent().getId().equals(studentId))
                .map(submission -> new SubmissionResponse(
                        new Exam(submission.getExam().getStudent(), test),
                        submission.getId(),
                        submission.getStatus(),
                        submission.getGrading())).collect(Collectors.toList());

    }

    public void updateQuestionGrading(Long testId, Long studentId, int questionNr, UpdateQuestionGradingRequest request) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test not found"));

        Optional<Submission> submissionOpt = test.getSubmissions().stream()
                .filter(submission -> submission.getExam().getStudent().getId().equals(studentId))
                .findFirst();

        if (submissionOpt.isPresent()) {
            Submission submission = submissionOpt.get();
            submission.updateGradingForQuestion(questionNr, request.getGivenPoints(), request.getFeedback());
            submissionRepository.save(submission);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Submission not found for the given student and test");
        }
    }


    public void addGrading(Long testId, Long studentId, GradingRequest request) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test not found"));

        Optional<Submission> submissionOpt = test.getSubmissions().stream()
                .filter(submission -> submission.getExam().getStudent().getId().equals(studentId))
                .findFirst();

        if (submissionOpt.isPresent()) {
            Submission submission = submissionOpt.get();
            Grading grading = new Grading(submission.calculateGrade(), request.getComments());
            submission.addGrading(grading);
            test.updateStatistics();
            testRepository.save(test);
            submissionRepository.save(submission);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Submission not found for the given student and test");
        }
    }
}