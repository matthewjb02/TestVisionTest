package nl.hu.inno.hulp.monoliet.testvision.domain.test;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;

@Entity
public class Submission {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Exam exam;

    @OneToOne
    private Grading grading;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    public Submission(Exam exam) {
        this.exam  = exam;
        this.status = SubmissionStatus.INGELEVERD;
    }

    public Submission() {
    }

    public void setGrading(Grading grading) {
        this.grading = grading;
    }

    public Long getId() {
        return id;
    }

    public SubmissionStatus getStatus() {
        return status;
    }
}