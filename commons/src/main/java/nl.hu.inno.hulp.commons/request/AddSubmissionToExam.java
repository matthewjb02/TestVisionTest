package nl.hu.inno.hulp.commons.request;

import lombok.Data;

@Data
public class AddSubmissionToExam {

    String examId;
    Long SubmissionId;

    public AddSubmissionToExam(String examId, Long submissionId) {
        this.examId = examId;
        this.SubmissionId = submissionId;
    }
}
