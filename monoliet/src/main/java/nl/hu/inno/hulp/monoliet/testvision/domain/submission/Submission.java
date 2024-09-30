package nl.hu.inno.hulp.monoliet.testvision.domain.submission;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamSession;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.Examination;

import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;

import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;

import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.GradingCriteria;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;


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
    }

    public Submission() {
    }

    public static Submission createSubmission(ExamSession examSession) {
        return new Submission(examSession);
    }

    public Long getId() {
        return id;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public ExamSession getExamSession() {
        return examSession;
    }

    public Grading getGrading() {
        return grading;
    }

    public void updateGradingForQuestion(int questionNr, int givenPoints, String feedback) {
        Question question = examSession.seeQuestion(questionNr);
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
        if (examSession.getExam().getQuestions().isEmpty() || examSession.getExam().getTotalPoints() == 0) {
            return 1.0;
        }

        // total points per question type
        int totalOpenPoints = this.examSession.getExam().getTotalOpenQuestionPoints();
        int totalMultipleChoicePoints = this.examSession.getExam().getTotalMultipleChoiceQuestionPoints();

        // weight per question type
        GradingCriteria criteria = examSession.getExam().getGradingCriteria();
        double openQuestionWeight = criteria.getOpenQuestionWeight();
        double multipleChoiceWeight = criteria.getClosedQuestionWeight();

        // calculate the total points given for each question type
        int totalOpenGivenPoints = calculateTotalOpenGivenPoints();
        int totalMultipleChoiceGivenPoints = calculateTotalMultipleChoiceGivenPoints();

        // calculate the weighted points for each question type
        double weightedOpenPoints = calculateWeightedPoints(totalOpenGivenPoints, openQuestionWeight);
        double weightedMultipleChoicePoints = calculateWeightedPoints(totalMultipleChoiceGivenPoints, multipleChoiceWeight);  // Gewijzigd

        // calculate the final grade
        double grade = (weightedOpenPoints + weightedMultipleChoicePoints) / (totalOpenPoints + totalMultipleChoicePoints) * 10 * 2;
        return Math.round(grade * 10.0) / 10.0;
    }


    public int calculateTotalOpenGivenPoints() {
        int totalOpenGivenPoints = 0;
        for (Question question : examSession.getExam().getQuestions()) {
            if (question instanceof OpenQuestion) {
                totalOpenGivenPoints += question.getGivenPoints();
            }
        }
        return totalOpenGivenPoints;
    }

    public int calculateTotalMultipleChoiceGivenPoints() {
        int totalMultipleChoiceGivenPoints = 0;
        for (Question question : examSession.getExam().getQuestions()) {
            if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
                if (mcQuestion.getCorrectAnswerIndex() == mcQuestion.getAnswer()) {
                    totalMultipleChoiceGivenPoints += mcQuestion.getPoints();
                }
            }
        }
        return totalMultipleChoiceGivenPoints;
    }

    public double calculateWeightedPoints(int totalGivenPoints, double weight) {
        return totalGivenPoints * weight;
    }



    public Student getStudentFromExamSubmission() {
        return examSession.getStudent();

    }

    public Long getStudentIDtFromExamSubmission() {
        return examSession.getStudentId();

    }

    public void addGrading(Grading grading) {
        this.grading = grading;
        this.status = SubmissionStatus.GRADED;
    }




}