package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request;

public class StartExamRequest {
    private Long studentId;
    private Long testId;

    public StartExamRequest(Long studentId, Long testId) {
        this.studentId = studentId;
        this.testId = testId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getTestId() {
        return testId;
    }
}
