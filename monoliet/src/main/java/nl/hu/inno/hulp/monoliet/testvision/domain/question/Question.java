package nl.hu.inno.hulp.monoliet.testvision.domain.question;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private int points;
    private String question;
    private String answer = "";
    private int givenPoints;
    private String teacher_feedback = "";

    protected Question() {
    }

    public Question(int points, String question) {
        this.points = points;
        this.question = question;
    }

    public Long getId(){
        return id;
    }

    public int getPoints() {
        return points;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void answerQuestion(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGivenPoints() {
        return givenPoints;

    }

    public String getTeacherFeedback() {
        return teacher_feedback;
    }

    public void addGivenPoints(int points) {
        this.givenPoints += points;
    }

    public void addTeacherFeedback(String feedback) {
        this.teacher_feedback += feedback;


    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}