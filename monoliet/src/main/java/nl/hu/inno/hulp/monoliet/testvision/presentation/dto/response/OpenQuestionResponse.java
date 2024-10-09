package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;

@Getter
public class OpenQuestionResponse extends QuestionResponse{
    private String correctAnswer;
    private String answer;
    private String teacherFeedback;

    public OpenQuestionResponse(OpenQuestion question) {
        super(question);
        this.correctAnswer = question.getCorrectAnswer();
        this.answer = question.getAnswer();
        this.teacherFeedback = question.getTeacherFeedback();
    }
}
