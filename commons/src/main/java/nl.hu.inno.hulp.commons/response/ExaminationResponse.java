package nl.hu.inno.hulp.commons.response;

import nl.hu.inno.hulp.commons.request.ExamDateDTO;

import java.util.List;

public class ExaminationResponse {
    private Long id;

    private List<StudentResponse> candidates;

    private List<ExamSessionResponse> examSessions;

    private ExamResponse exam;

    private String name;
    private String password;

    private ExamDateDTO examDate;

    private int duration;
    private int extraTime;

    public ExaminationResponse(Long id, List<StudentResponse> candidates, List<ExamSessionResponse> examSessions, ExamResponse exam, String name, String password, ExamDateDTO examDate, int duration, int extraTime) {
        this.id = id;
        this.candidates = candidates;
        this.examSessions = examSessions;
        this.exam = exam;
        this.name = name;
        this.password = password;
        this.examDate = examDate;
        this.duration = duration;
        this.extraTime = extraTime;
    }

    public ExamResponse getExam() {
        return exam;
    }
}
