package nl.hu.inno.hulp.monoliet.testvision.domain.question;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Arrays;
import java.util.List;

@Entity
public class MultipleChoiceQuestion extends QuestionEntity {
    @Id
    @GeneratedValue
    private Long id;

    private int points;
    private String question;
    private int givenPoints;
    private List<String> answers;
    private int correctAnswerIndex;
    private int answer;

    protected MultipleChoiceQuestion() {
    }

    public MultipleChoiceQuestion(int points, String question, int correctAnswerIndex, String... answers) {
        this.points = points;
        this.question = question;
        this.answers = Arrays.asList(answers);
        this.correctAnswerIndex = correctAnswerIndex;
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

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
