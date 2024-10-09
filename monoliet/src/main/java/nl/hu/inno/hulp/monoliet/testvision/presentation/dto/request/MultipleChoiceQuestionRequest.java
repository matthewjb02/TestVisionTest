package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class MultipleChoiceQuestionRequest extends QuestionRequest{

    private List<String> answers;
    private List<Integer> correctAnswerIndexes;

    public MultipleChoiceQuestionRequest(int points, String question, List<String> answers, List<Integer> correctAnswerIndexes) {
        super(points, question);
        this.answers = answers;
        this.correctAnswerIndexes = correctAnswerIndexes;
    }
}
