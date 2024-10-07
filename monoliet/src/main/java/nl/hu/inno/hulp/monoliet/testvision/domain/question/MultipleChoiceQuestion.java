package nl.hu.inno.hulp.monoliet.testvision.domain.question;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Entity
@Getter
public class MultipleChoiceQuestion extends QuestionEntity {
    @Id
    @GeneratedValue
    private Long id;

    private int points;
    private String question;
    private int givenPoints;
    private List<String> answers;
    private List<Integer> correctAnswerIndexes;
    private List<Integer> givenAnswers;

    protected MultipleChoiceQuestion() {
    }

    public MultipleChoiceQuestion(int points, String question, List<Integer> correctAnswerIndexes, List<String> answers) {
        this.points = points;
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndexes = correctAnswerIndexes;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public int getGivenPoints() {
        return givenPoints;
    }

    @Override
    public void addGivenPoints(int points) {
        throw new IllegalArgumentException("Points for multiple choice questions are automatically calculated.");
    }

    @Override
    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public List<Integer> getCorrectAnswerIndexes() {
        return correctAnswerIndexes;
    }

    public List<Integer> getGivenAnswers() {
        return givenAnswers;
    }

    public void setGivenAnswers(List<Integer> answers) {
        this.givenAnswers = answers;
    }
}
