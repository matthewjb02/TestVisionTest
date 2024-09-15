package nl.hu.inno.hulp.monoliet.testvision.presentation.dto;

import nl.hu.inno.hulp.monoliet.testvision.domain.Question;

public class QuestionResponse {
    private final int points;
    private final String question;
    private final String answer;

    public QuestionResponse(Question question) {
        this.points = question.getPoints();
        this.question = question.getQuestion();
        this.answer = question.getAnswer();
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
}
