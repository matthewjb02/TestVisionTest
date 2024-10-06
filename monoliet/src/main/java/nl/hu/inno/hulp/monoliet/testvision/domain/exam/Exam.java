package nl.hu.inno.hulp.monoliet.testvision.domain.exam;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
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

    public Exam(Teacher examMaker, Teacher examValidator, QuestionEntity... questions){
        if (questions.length > 0){
            this.questions = new ArrayList<>(Arrays.asList(questions));
            this.examMaker = examMaker;
            this.examValidator = examValidator;
            calculateTotalPoints();
        }
    }

    public void calculateTotalPoints(){
        if (questions == null){
            //TODO: Throw error
            return;
        }
        totalPoints = questions.stream().mapToInt(QuestionEntity::getPoints).sum();
    }

    public int getTotalPoints(){
        return  totalPoints;
    }

    public Long getId(){
        return id;
    }

    public List<QuestionEntity> getQuestions(){
        return questions;
    }

    public void removeQuestions(List<QuestionEntity> questions){
        this.questions.removeAll(questions);
    }

    public void addQuestions(List<QuestionEntity> questions){
        this.questions.addAll(questions);
    }

    public Teacher getExamValidatorMail() {
        return examValidator;
    }

    public Teacher getMakerMail() {
        return examMaker;
    }


    public ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public String getReason() {
        return reason;
    }

    public List<String> getQuestionsAsString() {
        return questions.stream()
                .map(QuestionEntity::getQuestion)
                .collect(Collectors.toList());
    }

    public void updateStatistics() {
        double passGrade = 5.5;

        int submissionCount = submissions.size();

        int passCount = (int) submissions.stream()
                .filter(submission -> submission.calculateGrade() >= passGrade)
                .count();

        int failCount = submissionCount - passCount;

        double averageScore = submissions.stream()
                .mapToDouble(Submission::calculateGrade)
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

    public GradingCriteria getGradingCriteria() {
        return gradingCriteria;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void addStatistics(Statistics statistics) {
        this.statistics = statistics;
    }


    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
