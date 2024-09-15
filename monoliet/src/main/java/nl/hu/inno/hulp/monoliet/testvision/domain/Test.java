package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Test {
    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    private GradingCriteria gradingCriteria;

    @OneToMany
    private List<Submission> submissions = new ArrayList<>();

    private Validation validationStatus= Validation.WAITING;
    private String reason;
  
    @OneToMany
    private List<Question> questions;
    private String testValidatorMail;

    @OneToOne(cascade = CascadeType.ALL)
    private Statistics statistics;

    private String makerMail;
    private int totalPoints;

    public Test(){

    }

    public Test(String makerMail, String testValidatorMail, Question... questions){
        if (questions.length > 0){
            this.questions = Arrays.asList(questions);
            this.makerMail = makerMail;
            this.testValidatorMail = testValidatorMail;
            calculateTotalPoints();
        }
    }

    public void calculateTotalPoints(){
        if (questions == null){
            //TODO: Throw error
            return;
        }
        totalPoints = questions.stream().mapToInt(Question::getPoints).sum();
    }

    public int getTotalPoints(){
        return  totalPoints;
    }
    public Long getId(){
        return id;
    }

    public List<Question> getQuestions(){
        return questions;
    }
    public void removeAllQuestions(List<Question> questions){
        this.questions.removeAll(questions);
    }
    public void addAllQuestion(List<Question> question){
        this.questions.addAll(question);
    }
    public String getTestValidatorMail() {
        return testValidatorMail;
    }

    public String getMakerMail() {
        return makerMail;
    }


    public Validation getValidationStatus() {
        return validationStatus;
    }

    public String getReason() {
        return reason;
    }

    public List<String> getQuestionsAsString() {
        return questions.stream()
                .map(Question::getQuestion)
                .collect(Collectors.toList());
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

    public void setTestValidatorMail(String testValidator) {
        this.testValidatorMail = testValidator;
    }

    public void setMakerMail(String maker) {
        this.makerMail = maker;
    }

    public void setValidationStatus(Validation validationStatus) {
        this.validationStatus = validationStatus;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
