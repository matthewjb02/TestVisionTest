package nl.hu.inno.hulp.grading.domain;

import jakarta.persistence.*;
import lombok.Getter;
import nl.hu.inno.hulp.commons.enums.SubmissionStatus;
import nl.hu.inno.hulp.commons.messaging.ExamSessionDTO;
import nl.hu.inno.hulp.commons.messaging.GradingDTO;


@Getter
@Entity
public class Submission {

    @Id
    @GeneratedValue
    private Long id;

    @Transient
    private ExamSessionDTO examSession;

    @Transient
    private Grading grading;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    private Submission(ExamSessionDTO examSession) {
        this.examSession = examSession;
        this.status = SubmissionStatus.SUBMITTED;
    }

    protected Submission() {
    }

    public static Submission createSubmission(ExamSessionDTO examSession) {
        return new Submission(examSession);
    }


    public void addGrading(Grading grading) {
        this.grading = grading;
        this.status = SubmissionStatus.GRADED;
    }
}