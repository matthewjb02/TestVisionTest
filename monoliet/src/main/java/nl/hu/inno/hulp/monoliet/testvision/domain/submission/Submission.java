package nl.hu.inno.hulp.monoliet.testvision.domain.submission;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.test.GradingCriteria;
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
        this.status = SubmissionStatus.SUBMITTED;
    }

    public Submission() {
    }

    public static Submission createSubmission(Exam exam) {
        return new Submission(exam);
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
            if(givenPoints > question.getPoints() || givenPoints < 0) {
                throw new IllegalArgumentException("Given points must be between 0 and the maximum points of the question");
            }
            question.addGivenPoints(givenPoints);

            if (question.getClass().equals(OpenQuestion.class)){
                OpenQuestion openQuestion = (OpenQuestion)question;
                openQuestion.setTeacherFeedback(feedback);
            }
        }


    }

    public double calculateGrade() {
        if(exam.getTest().getQuestions().isEmpty()) {
            return 1.0;
        }
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
        this.grading = grading;
        this.status = SubmissionStatus.GRADED;
    }




}