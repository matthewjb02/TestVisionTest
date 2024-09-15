package nl.hu.inno.hulp.monoliet.testvision.application;

import java.util.List;

public class CourseDTO {
    private Long id;
    private String name;
    private List<TestDTO> approvedTests;
    private List<TestDTO> rejectedTests;
    private List<TestDTO> validatingTests;

    public CourseDTO() {
    }

    public CourseDTO(Long id, String name, List<TestDTO> approvedTests, List<TestDTO> rejectedTests, List<TestDTO> validatingTests) {
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

    public List<TestDTO> getApprovedTests() {
        return approvedTests;
    }

    public List<TestDTO> getRejectedTests() {
        return rejectedTests;
    }

    public List<TestDTO> getValidatingTests() {
        return validatingTests;
    }
}
