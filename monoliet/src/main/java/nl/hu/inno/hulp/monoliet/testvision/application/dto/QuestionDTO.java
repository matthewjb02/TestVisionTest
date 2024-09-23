package nl.hu.inno.hulp.monoliet.testvision.application.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OpenQuestionDTO.class, name = "open"),
        @JsonSubTypes.Type(value = MultipleChoiceQuestionDTO.class, name = "multipleChoice")
})
public class QuestionDTO {

    private Long id;
    private int points;
    private String question;

    public QuestionDTO() {
    }

    public QuestionDTO(Long id, int points, String question) {
        this.id = id;
        this.points = points;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public String getQuestion() {
        return question;
    }
}
