package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import nl.hu.inno.hulp.monoliet.testvision.domain.Grading;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.SubmissionStatus;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.ExamResponse;

public class SubmissionResponse extends ExamResponse {
    private Long submissionId;
    private SubmissionStatus status;
    private Grading grading;

    public SubmissionResponse(Exam exam, Long submissionId, SubmissionStatus status, Grading grading) {
        super(exam);
        this.submissionId = submissionId;
        this.status = status;
        this.grading = grading;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }

    public Grading getGrading() {
        return grading;
    }

    public void setGrading(Grading grading) {
        this.grading = grading;
    }
}