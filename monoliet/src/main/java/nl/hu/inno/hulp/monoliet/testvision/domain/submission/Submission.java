package nl.hu.inno.hulp.monoliet.testvision.domain.submission;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
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
            question.addTeacherFeedback(feedback);
        }


    }

    public double calculateGrade() {
        if (exam.getTest().getQuestions().isEmpty()) {
            return 1.0;
        }

        GradingCriteria criteria = exam.getTest().getGradingCriteria();
        double openQuestionWeight = criteria.getOpenQuestionWeight();
        double closedQuestionWeight = criteria.getClosedQuestionWeight();

        int totalOpenGivenPoints = 0;
        int totalClosedGivenPoints = 0;
        int totalOpenPoints = 0;
        int totalClosedPoints = 0;

        for (Question question : exam.getTest().getQuestions()) {
            if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
                totalClosedPoints += mcQuestion.getPoints();
                if (mcQuestion.getCorrectAnswerIndex() == mcQuestion.getAnswer()) {
                    totalClosedGivenPoints += mcQuestion.getPoints();
                }
            } else {
                totalOpenPoints += question.getPoints();
                totalOpenGivenPoints += question.getGivenPoints();
            }
        }

        double weightedOpenPoints = (double) totalOpenGivenPoints / totalOpenPoints * openQuestionWeight;
        double weightedClosedPoints = (double) totalClosedGivenPoints / totalClosedPoints * closedQuestionWeight;

        double grade = (weightedOpenPoints + weightedClosedPoints) * 9 + 1;
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