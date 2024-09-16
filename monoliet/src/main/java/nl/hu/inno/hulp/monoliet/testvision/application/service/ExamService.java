package nl.hu.inno.hulp.monoliet.testvision.application.service;

import nl.hu.inno.hulp.monoliet.testvision.data.ExamRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.SubmissionRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.State;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.ExamInactiveException;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NoExamFoundException;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import nl.hu.inno.hulp.monoliet.testvision.domain.test.Test;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.AnswerRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.ExamRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.SeeQuestion;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.StartExamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ExamService {
    private final ExamRepository examRepository;
    private final StudentService studentService;
    private final TestService testService;
    private final SubmissionRepository submissionRepository;


    @Autowired
    public ExamService(ExamRepository examRepository, StudentService studentService, TestService testService, SubmissionRepository submissionRepository) {
        this.examRepository = examRepository;
        this.studentService = studentService;
        this.testService = testService;
        this.submissionRepository = submissionRepository;
    }

    public Exam startExam(StartExamRequest examRequest) {
        Student student = studentService.getStudent(examRequest.studentId());
        Test test = testService.getTest(examRequest.testId());
        Exam exam = new Exam(student, test);
        examRepository.save(exam);

        return exam;
    }

    public Question seeQuestion(SeeQuestion examRequest)  {
        Exam exam = getExamById(examRequest.examId());

        if (exam.getState() == State.Active) {
            return exam.seeQuestion(examRequest.questionNr());
        } else {
            throw new ExamInactiveException("This exam is inactive");
        }
    }

    public Exam enterAnswer(AnswerRequest answerRequest) {
        Exam exam = getExamById(answerRequest.examId());

        if (exam.getState() == State.Active) {
            exam.answerQuestion(answerRequest.questionNr(), answerRequest.answer());
            examRepository.save(exam);
            return exam;
        } else {
            throw new ExamInactiveException("This exam is inactive");
        }
    }

    public Exam endExam(ExamRequest examRequest) {
        Exam exam = getExamById(examRequest.examId());

        if (exam.getState() == State.Active) {
            exam.endExam();

            Submission submission = new Submission(exam);
            exam.getTest().addSubmission(submission);
            submissionRepository.save(submission);

            return exam;

        } else {
            throw new ExamInactiveException("This exam is already completed");
        }
    }

    public Exam getExamById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new NoExamFoundException("No exam with id: " + id + " found!"));
    }
}
