package nl.hu.inno.hulp.monoliet.testvision.domain.test;

import jakarta.persistence.*;

@Entity
public class Submission {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private TestAttempt testAttempt;

    @OneToOne
    private Grading grading;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    public Submission(TestAttempt testAttempt) {
        this.testAttempt = testAttempt;
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