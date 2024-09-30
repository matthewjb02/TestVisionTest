package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamSession;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamState;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;

public class ExamSessionResponse {
    private final Long id;
    private final ExamState status;
    private final int duration;
    private final Student student;
    private final ExamResponse exam;

    public ExamSessionResponse(ExamSession examSession) {
        this.id = examSession.getId();
        this.status = examSession.getState();
        this.student = examSession.getStudent();
        this.exam = new ExamResponse(examSession.getExam());
        this.duration = examSession.getDuration();
    }

    public Long getId() {
        return id;
    }

    public ExamState getStatus() {
        return status;
    }

    public int getDuration() {
        return duration;
    }

    public Student getStudent() {
        return student;
    }

    public ExamResponse getExam() {
        return exam;
    }
}
