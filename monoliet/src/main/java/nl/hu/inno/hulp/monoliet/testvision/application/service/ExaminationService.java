package nl.hu.inno.hulp.monoliet.testvision.application.service;

import nl.hu.inno.hulp.monoliet.testvision.data.ExaminationRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.SubmissionRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.Examination;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.State;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.ExaminationInactiveException;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NoExaminationFoundException;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.AnswerRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.ExaminationRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.SeeQuestion;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.StartExaminationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ExaminationService {
    private final ExaminationRepository examinationRepository;
    private final StudentService studentService;
    private final ExamService examService;
    private final SubmissionRepository submissionRepository;


    @Autowired
    public ExaminationService(ExaminationRepository examinationRepository, StudentService studentService, ExamService examService, SubmissionRepository submissionRepository) {
        this.examinationRepository = examinationRepository;
        this.studentService = studentService;
        this.examService = examService;
        this.submissionRepository = submissionRepository;
    }

    public Examination startExamination(StartExaminationRequest examinationRequest) {
        Student student = studentService.getStudent(examinationRequest.studentId());
        Exam exam = examService.getExam(examinationRequest.examId());
        Examination examination = new Examination(student, exam);
        examinationRepository.save(examination);

        return examination;
    }

    public Question seeQuestion(SeeQuestion examRequest)  {
        Examination examination = getExamById(examRequest.examId());

        if (examination.getState() == State.Active) {
            return examination.seeQuestion(examRequest.questionNr());
        } else {
            throw new ExaminationInactiveException("This exam is inactive");
        }
    }

    public Examination enterAnswer(AnswerRequest answerRequest) {
        Examination examination = getExamById(answerRequest.examId());

        if (examination.getState() == State.Active) {
            examination.answerQuestion(answerRequest.questionNr(), answerRequest.answer());
            examinationRepository.save(examination);
            return examination;
        } else {
            throw new ExaminationInactiveException("This exam is inactive");
        }
    }

    public Examination endExam(ExaminationRequest examinationRequest) {
        Examination examination = getExamById(examinationRequest.examId());

        if (examination.getState() == State.Active) {
            examination.endExam();

            Submission submission = Submission.createSubmission(examination);
            examination.getExam().addSubmission(submission);
            submissionRepository.save(submission);

            return examination;

        } else {
            throw new ExaminationInactiveException("This exam is already completed");
        }
    }

    public Examination getExamById(Long id) {
        return examinationRepository.findById(id)
                .orElseThrow(() -> new NoExaminationFoundException("No exam with id: " + id + " found!"));
    }
}
