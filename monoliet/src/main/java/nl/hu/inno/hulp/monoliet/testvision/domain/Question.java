package nl.hu.inno.hulp.monoliet.testvision.domain;

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

    protected Question(){
    }

    public Question(int points, String question) {
        this.points = points;
        this.question = question;
    }

    public Long getId(){
        return id;
    }

    public int getPoints(){
        return points;
    }

    public String getQuestion(){
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void answerQuestion(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public void setPoints(int points){
        this.points = points;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", points=" + points +
                ", question='" + question + '\'' +
                '}';
    }
}
