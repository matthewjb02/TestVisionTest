package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import nl.hu.inno.hulp.monoliet.testvision.domain.examination.Examination;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;

import java.util.List;

public class CandidatesResponse {
    public final Long examinationId;
    public final String name;
    public final List<Student> candidates;

    public CandidatesResponse(Examination examination) {
        this.examinationId = examination.getId();
        this.name = examination.getName();
        this.candidates = examination.getCandidates();
    }

    public Long getExaminationId() {
        return examinationId;
    }

    public String getName() {
        return name;
    }

    public List<Student> getCandidates() {
        return candidates;
    }
}
