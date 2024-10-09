package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;

import java.util.List;

@Getter
public class MultipleChoiceQuestionResponse extends QuestionResponse{
    private List<String> answers;
    private List<Integer> correctAnswerIndexes;
    private List<Integer> givenAnswers;

    public MultipleChoiceQuestionResponse(MultipleChoiceQuestion question) {
        super(question);
        this.answers = question.getAnswers();
        this.correctAnswerIndexes = question.getCorrectAnswerIndexes();
        this.givenAnswers = question.getGivenAnswers();
    }
}
