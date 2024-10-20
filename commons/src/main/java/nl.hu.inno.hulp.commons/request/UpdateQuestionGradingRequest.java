package nl.hu.inno.hulp.commons.request;

import lombok.Data;

@Data
public class UpdateQuestionGradingRequest {
    public  int givenPoints;
    public String feedback;


}