package nl.hu.inno.hulp.commons.response;

import lombok.Getter;
import nl.hu.inno.hulp.commons.enums.ExamState;
import nl.hu.inno.hulp.commons.enums.SubmissionStatus;


@Getter
public class SubmissionResponse extends ExamSessionResponse {
    private Long submissionId;
    private ExamState submissionStatus;
    private GradingResponse grading;

    public SubmissionResponse(ExamSessionResponse examSession, Long submissionId, ExamState status, GradingResponse grading) {
        super(examSession.getId(),status, examSession.getDuration(), examSession.getStudent());
        this.submissionId = submissionId;
        this.submissionStatus = status;
        this.grading = new GradingResponse(grading.getId(),grading.getGrade(), grading.getComments());
    }

//    public SubmissionResponse(Submission submission){
//        super(null);
//        this.submissionId = submission.getId();
//        this.submissionStatus = submission.getStatus();
//        this.grading = new GradingResponse(submission.getGrading());
//    }
}