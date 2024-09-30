package nl.hu.inno.hulp.monoliet.testvision.domain.examination;

import jakarta.persistence.*;
import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Entity
@Getter
public class Examination {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Student> candidates;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ExamSession> examSessions;

    @OneToOne(cascade = CascadeType.ALL)
    private Exam exam;

    private String name;
    private String password;

    @Embedded
    private ExamDate examDate;

    private int duration;
    private int extraTime;

    protected Examination() {
    }

    public Examination(String name, Exam exam, String password, ExamDate examDate, int duration, int extraTime) {
        this.name = name;
        this.exam = exam;
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

    public Examination selectCandidates(List<Student> candidates) {
        this.candidates = candidates;
        return this;
    }

    public List<ExamSession> setupExamSessions() {
        for (Student student : candidates) {
            ExamSession examSession = new ExamSession(this, student);
            storeExamSession(examSession);
        }

        return examSessions;
    }

    public void storeExamSession(ExamSession examSession) {
        examSessions.add(examSession);
    }

    public boolean validateStudent(Student student) {
        return candidates.contains(student);
    }
}
