package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Grading;
@Getter
public class GradingResponse {
    private Long id;
    private double grade;
    private String comments;

    public GradingResponse(Grading grading){
        this.id = grading.getId();
        this.grade = grading.getGrade();
        this.comments = grading.getComments();
    }
}
