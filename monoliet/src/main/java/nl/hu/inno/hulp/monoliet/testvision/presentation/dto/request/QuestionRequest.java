package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OpenQuestionRequest.class, name = "open"),
        @JsonSubTypes.Type(value = MultipleChoiceQuestionRequest.class, name = "multipleChoice")
})
public class QuestionRequest {
    private int points;
    private String question;

    public QuestionRequest(int points, String question) {
        this.points = points;
        this.question = question;
    }
}
