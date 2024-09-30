package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamSession;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Grading;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.SubmissionStatus;


public class SubmissionResponse extends ExamSessionResponse {
    private Long submissionId;
    private SubmissionStatus submissionStatus;
    private Grading grading;

    public SubmissionResponse(ExamSession examSession, Long submissionId, SubmissionStatus status, Grading grading) {
        super(examSession);
        this.submissionId = submissionId;
        this.submissionStatus = status;
        this.grading = grading;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    public Grading getGrading() {
        return grading;
    }


}