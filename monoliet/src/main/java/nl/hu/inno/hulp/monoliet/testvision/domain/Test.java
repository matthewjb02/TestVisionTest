package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Test {
    @Id
    @GeneratedValue
    private Long id;
    protected Validation validationStatus= Validation.WAITING;
    protected String reason;
    @OneToMany
    private List<Question> questions;
    protected String testValidatorMail;

    protected String makerMail;
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

    public List<String> getQuestionsAsString(){
        return questions.stream()
                .map(Question::getQuestion)
                .collect(Collectors.toList());
    }

    public void setTestValidatorMail(String testValidator) {
        this.testValidatorMail = testValidator;
    }

    public void setMakerMail(String maker) {
        this.makerMail = maker;
    }

}
