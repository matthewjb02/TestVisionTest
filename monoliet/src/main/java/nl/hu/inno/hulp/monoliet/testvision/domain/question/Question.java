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
    private String teacher_feedback = "";

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

    public String getTeacherFeedback() {
        return teacher_feedback;
    }

    public void addGivenPoints(int points) {
        if(this instanceof MultipleChoiceQuestion) {
            throw new IllegalArgumentException("Points for multiple choice questions are automatically calculated and cannot be manually assigned.");
        }
        this.givenPoints += points;
    }

    public void addTeacherFeedback(String feedback) {

        if(this instanceof MultipleChoiceQuestion) {
            throw new IllegalArgumentException("Points for multiple choice questions are automatically calculated and cannot be manually assigned. You can only provide feedback for open questions.");
        }
        this.teacher_feedback += feedback;
    }


}