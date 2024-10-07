package nl.hu.inno.hulp.monoliet.testvision.domain.question;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class OpenQuestion extends Question {

    private String correctAnswer;
    private String answer = "";
    private String teacherFeedback = "";

    protected OpenQuestion(){
    }

    public OpenQuestion(int points, String question, String correctAnswer){
        this.setPoints(points);
        this.setQuestion(question);
        this.correctAnswer = correctAnswer;
        this.answer = "";
    }



    public void addTeacherFeedback(String feedback) {
        this.teacherFeedback += feedback;
    }
}
