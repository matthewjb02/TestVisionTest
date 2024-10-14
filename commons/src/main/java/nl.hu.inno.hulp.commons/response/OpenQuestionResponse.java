package nl.hu.inno.hulp.commons.response;

import lombok.Getter;

@Getter
public class OpenQuestionResponse extends QuestionResponse{
    private String correctAnswer;
    private String answer;
    private String teacherFeedback;

    public OpenQuestionResponse(int points,String question,String correctAnswer, String answer, String teacherFeedback) {
        super(points,question);
        this.correctAnswer = correctAnswer;
        this.answer = answer;
        this.teacherFeedback = teacherFeedback;
    }
}
