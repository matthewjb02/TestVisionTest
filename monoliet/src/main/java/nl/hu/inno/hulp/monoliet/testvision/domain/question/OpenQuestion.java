package nl.hu.inno.hulp.monoliet.testvision.domain.question;

import jakarta.persistence.Entity;

@Entity
public class OpenQuestion extends Question {

    private String correctAnswer;
    private String answer;

    protected OpenQuestion(){
    }

    public OpenQuestion(int points, String question, String correctAnswer){
        this.setPoints(points);
        this.setQuestion(question);
        this.correctAnswer = correctAnswer;
        this.answer = "";
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public String getAnswer(){
        return answer;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }
}
