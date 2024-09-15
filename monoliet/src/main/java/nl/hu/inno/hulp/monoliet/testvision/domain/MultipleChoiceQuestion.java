package nl.hu.inno.hulp.monoliet.testvision.domain;

import java.util.Arrays;
import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<String> answers;
    private int correctAnswerIndex;

    protected MultipleChoiceQuestion(){
    }

    public MultipleChoiceQuestion(int points, String question, int correctAnswerIndex, String... answers) {
        this.setPoints(points);
        this.setQuestion(question);
        this.answers = Arrays.asList(answers);
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}
