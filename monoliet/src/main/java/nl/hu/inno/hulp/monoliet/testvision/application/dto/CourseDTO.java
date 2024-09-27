package nl.hu.inno.hulp.monoliet.testvision.application.dto;

import java.util.List;

public class CourseDTO {
    private Long id;
    private String name;
    private List<ExamDTO> approvedTests;
    private List<ExamDTO> rejectedTests;
    private List<ExamDTO> validatingTests;

    public CourseDTO() {
    }

    public CourseDTO(Long id, String name, List<ExamDTO> approvedTests, List<ExamDTO> rejectedTests, List<ExamDTO> validatingTests) {
        this.id = id;
        this.name = name;
        this.approvedTests = approvedTests;
        this.rejectedTests = rejectedTests;
        this.validatingTests = validatingTests;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ExamDTO> getApprovedTests() {
        return approvedTests;
    }

    public List<ExamDTO> getRejectedTests() {
        return rejectedTests;
    }

    public List<ExamDTO> getValidatingTests() {
        return validatingTests;
    }
}
