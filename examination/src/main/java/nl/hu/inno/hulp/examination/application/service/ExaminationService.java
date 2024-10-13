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
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ExaminationService {
    private final ExaminationRepository examinationRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public ExaminationService(ExaminationRepository examinationRepository, RestTemplate restTemplate) {
        this.examinationRepository = examinationRepository;
        this.restTemplate = restTemplate;
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

    public StudentResponse getCandidateById(Long id) {
        String url = "http://localhost:8081/student/" + id;
        return restTemplate.getForObject(url, StudentResponse.class);
    }

    public ExamResponse getExamById(Long id) {
        String url = "http://localhost:8082/exam/" + id;
        return restTemplate.getForObject(url, ExamResponse.class);
    }

    public CandidatesResponse getCandidatesResponse(Long id, List<Long> candidates) {
        Examination examination = getExaminationById(id);
        List<StudentResponse> studentResponses = candidates.stream()
                .map(this::getCandidateById)
                .collect(Collectors.toList());

        return new CandidatesResponse(examination.getId(), examination.getName(), studentResponses);
    }

    public ExaminationResponse getExaminationResponse(Long id) {
        Examination examination = getExaminationById(id);
        List<StudentResponse> studentResponses = examination.getCandidates().stream()
                .map(this::getCandidateById)
                .collect(Collectors.toList());

        List<ExamSessionResponse> examSessionResponses = new ArrayList<>();
        ExamResponse examResponse = getExamById(examination.getExamId());
        ExamDateDTO examDateDTO = new ExamDateDTO();

        return new ExaminationResponse(examination.getId(), studentResponses, examSessionResponses,
                examResponse, examination.getName(), examination.getPassword(), examDateDTO, examination.getDuration(),
                examination.getExtraTime());
    }
}
