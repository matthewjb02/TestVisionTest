package nl.hu.inno.hulp.monoliet.testvision.application;

import java.util.List;

public class CourseDTO {
    private Long id;
    private String name;
    private List<TestDTO> tests;

    public CourseDTO() {
    }

    public CourseDTO(Long id, String name, List<TestDTO> tests) {
        this.id = id;
        this.name = name;
        this.tests = tests;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<TestDTO> getTests() {
        return tests;
    }
}
