package nl.hu.inno.hulp.monoliet.testvision.domain.question;


import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;

public class OpenQuestion extends Question {
    private String answer;

    protected OpenQuestion(){
    }

    public OpenQuestion(int points, String question, String answer){
        this.setPoints(points);
        this.setQuestion(question);
        this.answer = answer;
    }
}
