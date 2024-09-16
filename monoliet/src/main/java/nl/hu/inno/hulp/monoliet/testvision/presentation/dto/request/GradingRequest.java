package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request;

public class GradingRequest {
    private String comments;
    private Long teacherId;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}