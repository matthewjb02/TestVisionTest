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

    public Question(){
    }

    public int getPoints(){
        return points;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public void setPoints(int points){
        this.points = points;
    }
}
