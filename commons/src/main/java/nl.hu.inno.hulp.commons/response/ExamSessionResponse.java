package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamSession;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamState;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
@Getter
public class ExamSessionResponse {
    private final Long id;
    private final ExamState status;
    private final int duration;
    private final Student student;

    public ExamSessionResponse(ExamSession examSession) {
        this.id = examSession.getId();
        this.status = examSession.getState();
        this.student = examSession.getStudent();
        this.duration = examSession.getDuration();
    }

}
