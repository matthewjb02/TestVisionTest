package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import nl.hu.inno.hulp.monoliet.testvision.domain.examination.Examination;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Grading;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.SubmissionStatus;


public class SubmissionResponse extends ExaminationResponse {
    private Long submissionId;
    private SubmissionStatus status;
    private Grading grading;

    public SubmissionResponse(Examination examination, Long submissionId, SubmissionStatus status, Grading grading) {
        super(examination);
        this.submissionId = submissionId;
        this.status = status;
        this.grading = grading;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public Grading getGrading() {
        return grading;
    }


}