package nl.hu.inno.hulp.monoliet.testvision.application.dto;

public class OpenQuestionDTO extends QuestionDTO{

    private String correctAnswer;
    private String answer;

    public OpenQuestionDTO() {
    }

    public OpenQuestionDTO(Long id, int points, String question, String correctAnswer, String answer) {
        super(id, points, question);
        this.correctAnswer = correctAnswer;
        this.answer = answer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getAnswer() {
        return answer;
    }
}
