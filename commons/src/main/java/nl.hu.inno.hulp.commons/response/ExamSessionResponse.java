package nl.hu.inno.hulp.commons.response;

import lombok.Getter;
import nl.hu.inno.hulp.commons.enums.ExamState;
import nl.hu.inno.hulp.commons.enums.SubmissionStatus;

@Getter
public class ExamSessionResponse {
    private final Long id;
    private final ExamState status;
    private final int duration;
    private final StudentResponse student;

    public ExamSessionResponse(Long id, ExamState status, int duration, StudentResponse student) {
        this.id = id;
        this.status = status;
        this.duration = duration;
        this.student = student;
    }

}
