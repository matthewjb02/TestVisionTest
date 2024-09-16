package nl.hu.inno.hulp.monoliet.testvision.domain.submission;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;


@Entity
public class Submission {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Exam exam;

    @OneToOne(cascade = CascadeType.ALL)
    private Grading grading;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    public Submission(Exam exam) {
        this.exam = exam;
        this.status = SubmissionStatus.INGELEVERD;
    }

    public Submission() {
    }


    public Long getId() {
        return id;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public Exam getExam() {
        return exam;
    }

    public Grading getGrading() {
        return grading;
    }

    public void updateGradingForQuestion(int questionNr, int givenPoints, String feedback) {
        Question question = exam.seeQuestion(questionNr);
        if (question != null) {
            question.addGivenPoints(givenPoints);
            question.addTeacherFeedback(feedback);
        }


    }

    public double calculateGrade() {
        int totalGivenPoints = exam.getTest().getQuestions().stream()
                .mapToInt(Question::getGivenPoints)
                .sum();
        int totalPoints = exam.getTest().getTotalPoints();
        double grade = ((double) totalGivenPoints / totalPoints) * 9 + 1;
        return Math.round(grade * 10.0) / 10.0;
    }

    public Student getStudentFromExamSubmission() {
        return exam.getStudent();

    }

    public Long getStudentIDtFromExamSubmission() {
        return exam.getStudentId();

    }

    public void addGrading(Grading grading) {
    }
}