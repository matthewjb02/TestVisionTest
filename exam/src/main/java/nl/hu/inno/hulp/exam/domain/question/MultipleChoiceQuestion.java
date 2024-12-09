package nl.hu.inno.hulp.exam.domain.question;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Scope;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Document
@Getter
@Scope("question")
@Collection("multipleChoiceQuestion")
public class MultipleChoiceQuestion extends QuestionEntity implements Serializable {

    @ElementCollection
    private List<String> answers;
    @ElementCollection
    private List<Integer> correctAnswerIndexes;
    @ElementCollection
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
    public void addGivenPoints(int points) {
        throw new IllegalArgumentException("Points for multiple choice questions are automatically calculated.");
    }

    public void setGivenAnswers(List<Integer> givenAnswers){
        this.givenAnswers = givenAnswers;
    }
}
