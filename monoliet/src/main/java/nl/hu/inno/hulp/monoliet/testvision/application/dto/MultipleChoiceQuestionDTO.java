package nl.hu.inno.hulp.monoliet.testvision.application.dto;

import java.util.List;

public class MultipleChoiceQuestionDTO extends QuestionDTO{

    private List<String> answers;
    private int correctAnswerIndex;
    private int answer;

    public MultipleChoiceQuestionDTO(Long id, int points, String question, double givenPoints, List<String> answers, int correctAnswerIndex, int answer) {
        super(id, points, question, givenPoints);
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
        this.answer = answer;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public int getAnswer() {
        return answer;
    }
}
