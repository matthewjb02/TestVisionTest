package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;

@Getter
public class QuestionResponse {
    private int points;
    private String question;

    public QuestionResponse(QuestionEntity question) {
        this.points = question.getPoints();
        this.question = question.getQuestion();
    }
}
