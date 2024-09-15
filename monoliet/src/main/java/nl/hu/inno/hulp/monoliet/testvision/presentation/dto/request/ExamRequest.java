package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request;

public class ExamRequest {
    private final Long examId;

    public ExamRequest(Long examId) {
        this.examId = examId;
    }

    public Long getExamId() {
        return examId;
    }
}
