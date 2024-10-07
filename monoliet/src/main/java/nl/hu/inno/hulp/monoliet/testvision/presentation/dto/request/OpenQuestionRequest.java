package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request;

public class OpenQuestionRequest extends QuestionRequest{

    private String correctAnswer;

    public OpenQuestionRequest(int points, String question, String correctAnswer) {
        super(points, question);
        this.correctAnswer = correctAnswer;
    }
}
