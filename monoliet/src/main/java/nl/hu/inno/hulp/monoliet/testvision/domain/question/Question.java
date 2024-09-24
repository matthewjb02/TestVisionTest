package nl.hu.inno.hulp.monoliet.testvision.domain.question;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

@Entity
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
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private int points;
    private String question;
    private int givenPoints;

    protected Question() {
    }

    public Question(int points, String question) {
        this.points = points;
        this.question = question;
    }

    public Long getId(){
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
        this.givenPoints += points;
    }
}