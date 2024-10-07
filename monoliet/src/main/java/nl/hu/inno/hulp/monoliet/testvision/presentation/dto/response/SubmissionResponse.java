package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamSession;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Grading;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.SubmissionStatus;


@Getter
public class SubmissionResponse extends ExamSessionResponse {
    private Long submissionId;
    private SubmissionStatus submissionStatus;
    private GradingResponse grading;

    public SubmissionResponse(ExamSession examSession, Long submissionId, SubmissionStatus status, Grading grading) {
        super(examSession);
        this.submissionId = submissionId;
        this.submissionStatus = status;
        this.grading = new GradingResponse(grading);
    }

//    public SubmissionResponse(Submission submission){
//        super(null);
//        this.submissionId = submission.getId();
//        this.submissionStatus = submission.getStatus();
//        this.grading = new GradingResponse(submission.getGrading());
//    }
}