package nl.hu.inno.hulp.monoliet.testvision.application.dto;

import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.ExamResponse;

import java.util.List;

public class CourseDTO {
    private Long id;
    private String name;
    private List<TeacherDTO> teachers;
    private List<ExamResponse> approvedTests;
    private List<ExamResponse> rejectedTests;
    private List<ExamResponse> validatingTests;

    public CourseDTO() {
    }

    public CourseDTO(Long id, String name, List<TeacherDTO> teachers, List<ExamResponse> approvedTests, List<ExamResponse> rejectedTests, List<ExamResponse> validatingTests) {
        this.id = id;
        this.name = name;
        this.teachers = teachers;
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

    public List<ExamResponse> getApprovedTests() {
        return approvedTests;
    }

    public List<ExamResponse> getRejectedTests() {
        return rejectedTests;
    }

    public List<ExamResponse> getValidatingTests() {
        return validatingTests;
    }
    public List<TeacherDTO> getTeachers() {
        return teachers;
    }
}
