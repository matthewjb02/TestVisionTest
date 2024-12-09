package nl.hu.inno.hulp.exam.domain.question;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Scope;

import java.util.UUID;

@Document
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OpenQuestion.class, name = "open"),
        @JsonSubTypes.Type(value = MultipleChoiceQuestion.class, name = "multipleChoice")
})
public class QuestionEntity implements Question{
    @Id

    protected String id= UUID.randomUUID().toString();
@Field
    protected int points;
@Field
    protected String question;
@Field
    protected int givenPoints;

    protected QuestionEntity() {
    }

    public QuestionEntity(int points, String question) {
        this.points = points;
        this.question = question;
    }

    public String getId(){
        return id;
    }

    public int getPoints() {
        return points;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGivenPoints() {
        return givenPoints;
    }

    public void addGivenPoints(int points) {
        if(this instanceof MultipleChoiceQuestion) {
            throw new IllegalArgumentException("Points for multiple choice questions are automatically calculated and cannot be manually assigned.");
        }
        this.givenPoints += points;
    }
}