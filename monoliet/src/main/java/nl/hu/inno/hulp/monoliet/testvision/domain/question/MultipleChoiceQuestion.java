package nl.hu.inno.hulp.monoliet.testvision.domain.question;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter

public class MultipleChoiceQuestion extends Question {
    private List<String> answers;
    private int correctAnswerIndex;
    private int answer;

    protected MultipleChoiceQuestion(){
    }

    public MultipleChoiceQuestion(int points, String question, int correctAnswerIndex, String... answers) {
        this.setPoints(points);
        this.setQuestion(question);
        this.answers = Arrays.asList(answers);
        this.correctAnswerIndex = correctAnswerIndex;
    }



}
