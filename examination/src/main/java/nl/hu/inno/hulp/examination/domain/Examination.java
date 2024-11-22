package nl.hu.inno.hulp.examination.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Examination {
    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    private List<Long> candidates = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<ExamSession> examSessions;

    @Column(name = "exam_id")
    private Long examId;

    @Column(name = "course_id")
    private Long courseId;

    private String name;
    private String password;

    @Embedded
    private ExamDate examDate;

    private int duration;
    private int extraTime;

    protected Examination() {
    }

    public Examination(String name, Long examId, String password, ExamDate examDate, int duration, int extraTime,long courseId) {
        this.name = name;
        this.examId = examId;
        this.password = password;
        this.examDate = examDate;
        this.duration = duration;
        this.extraTime = extraTime;
        this.courseId= courseId;
    }

    public int totalDuration(boolean extraTimeRight) {
        if (extraTimeRight) {
            return duration + extraTime;
        }

        return duration;
    }

    public List<Long> selectCandidates(List<Long> candidates) {
        this.candidates.addAll(candidates);
        return this.candidates;
    }
    public List<Long> selectCandidate(Long studentId) {
        if (!candidates.contains(studentId)) {
            this.candidates.add(studentId);
        }
        return this.candidates;
    }

    public List<Long> removeCandidates(List<Long> candidates) {
        this.candidates.removeAll(candidates);
        return this.candidates;
    }
    public List<Long> removeCandidate(Long studentId) {
        if (!candidates.contains(studentId)) {
            this.candidates.remove(studentId);
        }
        return this.candidates;
    }

    public boolean storeExamSession(ExamSession examSession) {
        return examSessions.add(examSession);
    }

    public boolean validateStudent(Long studentId) {
        return candidates.contains(studentId);
    }
}
