package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.MultipleChoiceQuestionDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.OpenQuestionDTO;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;

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
