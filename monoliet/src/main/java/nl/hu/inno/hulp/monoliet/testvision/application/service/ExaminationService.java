package nl.hu.inno.hulp.monoliet.testvision.application.service;

import nl.hu.inno.hulp.monoliet.testvision.data.ExamSessionRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.ExaminationRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.SubmissionRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamDate;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamSession;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.Examination;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamState;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.ExaminationInactiveException;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NoExaminationFoundException;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ExaminationService {
    private final ExaminationRepository examinationRepository;
    private final ExamSessionRepository examSessionRepository;
    private final StudentService studentService;
    private final ExamService examService;


    @Autowired
    public ExaminationService(ExaminationRepository examinationRepository, ExamSessionRepository examSessionRepository,
                              StudentService studentService, ExamService examService) {
        this.examinationRepository = examinationRepository;
        this.examSessionRepository = examSessionRepository;
        this.studentService = studentService;
        this.examService = examService;
    }

    public Examination createExamination(CreateExamination request) {
        Exam exam = examService.getExam(request.examId());
        ExamDate examDate = new ExamDate(request.examDate().startDate, request.examDate().endDate);
        Examination examination = new Examination(request.name(), exam, request.password(), examDate, request.duration(), request.extraTime());
        examinationRepository.save(examination);

        return examination;
    }

    public Examination createSessions(Long id) {
        Examination examination = getExaminationById(id);
        examination.setupExamSessions();
        examSessionRepository.saveAll(examination.getExamSessions());

        return examination;
    }

    public Examination selectCandidates(Candidates candidates) {
        Examination examination = getExaminationById(candidates.examinationId);
        return examination.selectCandidates(candidates.students);
    }

    public boolean validatingStudent(StartExamSession request) {
        Examination examination = getExaminationById(request.examinationId());
        Student student = studentService.getStudent(request.studentId());

        return examination.validateStudent(student);
    }

    public Examination getExaminationById(Long id) {
        return examinationRepository.findById(id)
                .orElseThrow(() -> new NoExaminationFoundException("No examination with id: " + id + " found!"));
    }
}
