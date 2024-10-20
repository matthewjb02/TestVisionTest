package nl.hu.inno.hulp.commons.request;

import lombok.Data;

@Data
public class UpdateOpenQuestionPoints {

    public long examId;
    public int questionNr;
    public UpdateQuestionGradingRequest grading;

    public UpdateOpenQuestionPoints(long examId, int questionNr, UpdateQuestionGradingRequest grading) {
        this.examId = examId;
        this.questionNr = questionNr;
        this.grading = grading;
    }


}
