package nl.hu.inno.hulp.examination.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Examination {
    @Id
    @GeneratedValue
    private Long id;

    @Transient
    private List<Long> candidates;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ExamSession> examSessions;

    @Transient
    private Long examId;

    private String name;
    private String password;

    @Embedded
    private ExamDate examDate;

    private int duration;
    private int extraTime;

    protected Examination() {
    }

    public Examination(String name, Long examId, String password, ExamDate examDate, int duration, int extraTime) {
        this.name = name;
        this.examId = examId;
        this.password = password;
        this.examDate = examDate;
        this.duration = duration;
        this.extraTime = extraTime;
    }

    public int totalDuration(boolean extraTimeRight) {
        if (extraTimeRight) {
            return duration + extraTime;
        }

        return duration;
    }

    public Examination selectCandidates(List<Long> candidates) {
        this.candidates.addAll(candidates);
        return this;
    }
    public Examination selectCandidate(Long studentId) {
        this.candidates.add(studentId);
        return this;
    }

    public Examination removeCandidates(List<Long> candidates) {
        this.candidates.removeAll(candidates);
        return this;
    }
    public Examination removeCandidate(Long studentId) {
        this.candidates.remove(studentId);
        return this;
    }

    public boolean storeExamSession(ExamSession examSession) {
        return examSessions.add(examSession);
    }

    public boolean validateStudent(Long studentId) {
        return candidates.contains(studentId);
    }
}
