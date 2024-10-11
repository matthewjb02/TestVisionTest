package nl.hu.inno.hulp.examination.application.service;

import nl.hu.inno.hulp.commons.exception.NoExaminationFoundException;
import nl.hu.inno.hulp.commons.request.*;
import nl.hu.inno.hulp.commons.response.*;
import nl.hu.inno.hulp.examination.data.ExaminationRepository;
import nl.hu.inno.hulp.examination.domain.ExamDate;
import nl.hu.inno.hulp.examination.domain.ExamSession;
import nl.hu.inno.hulp.examination.domain.Examination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ExaminationService {
    private final ExaminationRepository examinationRepository;

    @Autowired
    public ExaminationService(ExaminationRepository examinationRepository) {
        this.examinationRepository = examinationRepository;
    }

    public ExaminationResponse createExamination(CreateExamination request) {
        ExamDate examDate = new ExamDate(request.examDate().startDate, request.examDate().endDate);
        Examination examination = new Examination(request.name(), request.examId(), request.password(), examDate,
                request.duration(), request.extraTime());
        examinationRepository.save(examination);

        return getExaminationResponse(examination.getId());
    }

    public CandidatesResponse selectCandidates(Candidates candidates) {
        Examination examination = getExaminationById(candidates.examinationId);
        return getCandidatesResponse(examination.getId(), examination.selectCandidates(candidates.studentIds));
    }

    public CandidatesResponse selectCandidate(Candidate candidate) {
        Examination examination = getExaminationById(candidate.examinationId);
        return getCandidatesResponse(examination.getId(), examination.selectCandidate(candidate.studentId));
    }

    public CandidatesResponse removeCandidates(Candidates candidates) {
        Examination examination = getExaminationById(candidates.examinationId);
        return getCandidatesResponse(examination.getId(), examination.removeCandidates(candidates.studentIds));
    }

    public CandidatesResponse removeCandidate(Candidate candidate) {
        Examination examination = getExaminationById(candidate.examinationId);
        return getCandidatesResponse(examination.getId(), examination.removeCandidate(candidate.studentId));
    }

    public boolean validatingStudent(StartExamSession request) {
        Examination examination = getExaminationById(request.examinationId());
        return examination.validateStudent(request.studentId());
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

    public CandidatesResponse getCandidatesResponse(Long id, List<Long> candidates) {
        Examination examination = getExaminationById(id);
        List<StudentResponse> studentResponses = new ArrayList<>();

        return new CandidatesResponse(examination.getId(), examination.getName(), studentResponses);
    }

    public ExaminationResponse getExaminationResponse(Long id) {
        Examination examination = getExaminationById(id);
        List<StudentResponse> studentResponses = new ArrayList<>();
        List<ExamSessionResponse> examSessionResponses = new ArrayList<>();
        ExamResponse examResponse = new ExamResponse();
        ExamDateDTO examDateDTO = new ExamDateDTO();

        return new ExaminationResponse(examination.getId(), studentResponses, examSessionResponses,
                examResponse, examination.getName(), examination.getPassword(), examDateDTO, examination.getDuration(),
                examination.getExtraTime());
    }
}
