package nl.hu.inno.hulp.examination.application.service;

import nl.hu.inno.hulp.commons.exception.NoExaminationFoundException;
import nl.hu.inno.hulp.commons.request.Candidate;
import nl.hu.inno.hulp.commons.request.Candidates;
import nl.hu.inno.hulp.commons.request.CreateExamination;
import nl.hu.inno.hulp.commons.request.StartExamSession;
import nl.hu.inno.hulp.examination.data.ExaminationRepository;
import nl.hu.inno.hulp.examination.domain.ExamDate;
import nl.hu.inno.hulp.examination.domain.ExamSession;
import nl.hu.inno.hulp.examination.domain.Examination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ExaminationService {
    private final ExaminationRepository examinationRepository;
    private final StudentService studentService;
    private final ExamService examService;


    @Autowired
    public ExaminationService(ExaminationRepository examinationRepository, StudentService studentService,
                              ExamService examService) {
        this.examinationRepository = examinationRepository;
        this.studentService = studentService;
        this.examService = examService;
    }

    public Examination createExamination(CreateExamination request) {
        Exam exam = examService.getExam(request.examId());
        ExamDate examDate = new ExamDate(request.examDate().startDate, request.examDate().endDate);
        Examination examination = new Examination(request.name(), exam, request.password(), examDate,
                request.duration(), request.extraTime());
        examinationRepository.save(examination);

        return examination;
    }

    public Examination selectCandidates(Candidates candidates) {
        Examination examination = getExaminationById(candidates.examinationId);
        return examination.selectCandidates(candidates.students);
    }

    public Examination selectCandidate(Candidate candidate) {
        Examination examination = getExaminationById(candidate.examinationId);
        return examination.selectCandidate(candidate.student);
    }

    public Examination removeCandidates(Candidates candidates) {
        Examination examination = getExaminationById(candidates.examinationId);
        return examination.removeCandidates(candidates.students);
    }

    public Examination removeCandidate(Candidate candidate) {
        Examination examination = getExaminationById(candidate.examinationId);
        return examination.removeCandidate(candidate.student);
    }

    public boolean validatingStudent(StartExamSession request) {
        Examination examination = getExaminationById(request.examinationId());
        Student student = studentService.getStudent(request.studentId());

        return examination.validateStudent(student);
    }

    public boolean storeExamSession(ExamSession examSession) {
        Examination examination = getExaminationById(examSession.getExaminationId());
        return examination.storeExamSession(examSession);
    }

    public void deleteExamination(Long id) {
        examinationRepository.delete(getExaminationById(id));
    }

    public Examination getExaminationById(Long id) {
        return examinationRepository.findById(id)
                .orElseThrow(() -> new NoExaminationFoundException("No examination with id: " + id + " found!"));
    }
}
