package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request;

import lombok.Getter;

@Getter
public class UpdateQuestionGradingRequest {
    private int givenPoints;
    private String feedback;

}