package nl.hu.inno.hulp.commons.response;

import lombok.Getter;
import nl.hu.inno.hulp.commons.dto.ExamDTO;

import java.util.List;

@Getter
public class CourseResponse {
        private Long id;
        private String name;
        private List<TeacherResponse> teachers;
        private List<ExamDTO> approvedTests;
        private List<ExamDTO> rejectedTests;
        private List<ExamDTO> validatingTests;

        protected CourseResponse() {
        }

    public CourseResponse(Long id, String name, List<TeacherResponse> teachers, List<ExamDTO> approvedTests, List<ExamDTO> rejectedTests, List<ExamDTO> validatingTests) {
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

    public List<TeacherResponse> getTeachers() {
        return teachers;
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
