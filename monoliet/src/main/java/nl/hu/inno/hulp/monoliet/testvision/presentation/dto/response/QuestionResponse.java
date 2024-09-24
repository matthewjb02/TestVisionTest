package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;

public class QuestionResponse {
    private final int points;
    private final String question;

    public QuestionResponse(Question question) {
        this.points = question.getPoints();
        this.question = question.getQuestion();
    }

    public String getQuestion() {
        return question;
    }
}
