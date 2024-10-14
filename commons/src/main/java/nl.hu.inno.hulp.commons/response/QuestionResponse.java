package nl.hu.inno.hulp.commons.response;

import lombok.Getter;

@Getter
public class QuestionResponse {
    private int points;
    private String question;

    public QuestionResponse(int points, String question) {
        this.points = points;
        this.question = question;
    }
}
