package nl.hu.inno.hulp.monoliet.testvision.domain.submission;

import jakarta.persistence.*;
import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamSession;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;

@Getter
@Entity
public class Submission {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private ExamSession examSession;

    @OneToOne(cascade = CascadeType.ALL)
    private Grading grading;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    public Submission(ExamSession examSession) {
        this.examSession = examSession;
        this.status = SubmissionStatus.SUBMITTED;
        this.grading = new Grading();
    }

    protected Submission() {
    }

    public static Submission createSubmission(ExamSession examSession) {
        return new Submission(examSession);
    }

    public void updateGradingForQuestion(int questionNr, int givenPoints, String feedback) {
        examSession.getExam().updateGradingForQuestion(this.examSession, questionNr, givenPoints, feedback);
    }

    public double calculateGrade() {
        return examSession.getExam().calculateGrade();
    }

    public void addGrading(Grading grading) {
        this.grading = grading;
        this.status = SubmissionStatus.GRADED;
    }
}