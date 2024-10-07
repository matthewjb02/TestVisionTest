package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.ExamDTO;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;

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

        public CourseResponse(Course course) {
            this.id = course.getId();
            this.name = course.getName();
            if (course.getTeachers() != null) {
                 for (Teacher t:course.getTeachers()){
                     this.teachers.add(new TeacherResponse(t));}
            }
            else{
                this.teachers = null;
            }

//            this.approvedTests = course.getApprovedExams();
//            this.rejectedTests = course.getRejectedExams();
//            this.validatingTests = course.getValidatingExams();
        }
}
