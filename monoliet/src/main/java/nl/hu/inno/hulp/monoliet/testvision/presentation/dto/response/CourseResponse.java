package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.ExamDTO;

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
}
