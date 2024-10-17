package nl.hu.inno.hulp.monoliet.testvision.domain.exam;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamSession;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Exam {
    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    private GradingCriteria gradingCriteria;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Submission> submissions = new ArrayList<>();

    private ValidationStatus validationStatus= ValidationStatus.WAITING;
    private String reason;

    @OneToMany
    private List<QuestionEntity> questions;
    @OneToOne
    private Teacher examValidator;

    @OneToOne(cascade = CascadeType.ALL)
    private Statistics statistics;
    @OneToOne
    private Teacher examMaker;
    private int totalPoints;
    protected Exam(){

    }

    public Exam(Teacher examMaker, Teacher examValidator){
            this.examMaker = examMaker;
            this.examValidator = examValidator;
            this.questions = new ArrayList<>();
            calculateTotalPoints();

    }

    public void calculateTotalPoints(){

        totalPoints = questions.stream().mapToInt(QuestionEntity::getPoints).sum();
    }



    public double calculateGrade() {
        if (questions.isEmpty() || totalPoints == 0) {
            return 1.0;
        }

        int totalOpenPoints = getTotalOpenQuestionPoints();
        int totalMultipleChoicePoints = getTotalMultipleChoiceQuestionPoints();

        double openQuestionWeight = gradingCriteria.getOpenQuestionWeight();
        double multipleChoiceWeight = gradingCriteria.getClosedQuestionWeight();

        int totalOpenGivenPoints = calculateTotalOpenGivenPoints();
        int totalMultipleChoiceGivenPoints = calculateTotalMultipleChoiceGivenPoints();

        double weightedOpenPoints = calculateWeightedPoints(totalOpenGivenPoints, openQuestionWeight);
        double weightedMultipleChoicePoints = calculateWeightedPoints(totalMultipleChoiceGivenPoints, multipleChoiceWeight);

        double grade = (weightedOpenPoints + weightedMultipleChoicePoints) / (totalOpenPoints + totalMultipleChoicePoints) * 10 * 2;
        return Math.round(grade * 10.0) / 10.0;
    }

    private int calculateTotalOpenGivenPoints() {
        int totalOpenGivenPoints = 0;
        for (QuestionEntity question : questions) {
            if (question instanceof OpenQuestion) {
                totalOpenGivenPoints += question.getGivenPoints();
            }
        }
        return totalOpenGivenPoints;
    }

    private int calculateTotalMultipleChoiceGivenPoints() {
        int totalMultipleChoiceGivenPoints = 0;
        for (QuestionEntity question : questions) {
            if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
                if (mcQuestion.getCorrectAnswerIndexes().equals(mcQuestion.getGivenAnswers())) {
                    totalMultipleChoiceGivenPoints += mcQuestion.getPoints();
                }
            }
        }
        return totalMultipleChoiceGivenPoints;
    }

    private double calculateWeightedPoints(int totalGivenPoints, double weight) {
        return totalGivenPoints * weight;
    }


    public void removeQuestions(List<QuestionEntity> questions){
        this.questions.removeAll(questions);
    }

    public void addQuestions(List<QuestionEntity> questions){
        this.questions.addAll(questions);
    }

    public void updateStatistics() {
        double passGrade = 5.5;

        int submissionCount = submissions.size();

        int passCount = (int) submissions.stream()
                .filter(submission -> this.calculateGrade() >= passGrade)
                .count();

        int failCount = submissionCount - passCount;

        double averageScore = submissions.stream()
                .mapToDouble(submission -> this.calculateGrade())
                .average()
                .orElse(0);

        statistics = new Statistics(submissionCount, passCount, failCount, averageScore);
    }

    public int getTotalOpenQuestionPoints(){
        return questions.stream()
                .filter(question -> question instanceof OpenQuestion)
                .mapToInt(QuestionEntity::getPoints)
                .sum();
    }

    public int getTotalMultipleChoiceQuestionPoints(){
       return questions.stream()
                .filter(question -> question instanceof MultipleChoiceQuestion)
                .mapToInt(QuestionEntity::getPoints)
                .sum();
    }

    public void addExamMaker(Teacher examMaker) {
        this.examMaker = examMaker;
    }
    public void addExamValidator(Teacher examValidator) {
        this.examValidator = examValidator;
    }
    public void addGradingCriteria(GradingCriteria gradingCriteria) {
        this.gradingCriteria = gradingCriteria;
    }

    public void addSubmission(Submission submission) {
        this.submissions.add(submission);
    }

    public void addStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
