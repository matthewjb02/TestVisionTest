package nl.hu.inno.hulp.commons.response;

import lombok.Getter;
import nl.hu.inno.hulp.commons.enums.ExamState;
import nl.hu.inno.hulp.commons.enums.SubmissionStatus;


@Getter
public class SubmissionResponse extends ExamSessionResponse {
    private Long submissionId;
    private SubmissionStatus submissionStatus;
    private GradingResponse grading;

    public SubmissionResponse(ExamSessionResponse examSession, Long submissionId, SubmissionStatus submissionStatus, GradingResponse grading) {
        super(examSession.getId(),examSession.getStatus(), examSession.getDuration(), examSession.getStudent());
        this.submissionId = submissionId;
        this.submissionStatus = submissionStatus;
        this.grading = new GradingResponse(grading.getId(),grading.getGrade(), grading.getComments());
    }

}