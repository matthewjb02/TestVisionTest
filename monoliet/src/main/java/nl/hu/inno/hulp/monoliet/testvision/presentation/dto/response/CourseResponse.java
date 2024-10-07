package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            else if(course.getTeachers()==null){
                this.teachers = null;
            }
            if(course.getValidatingExams()!=null){
                for (Exam exam:course.getValidatingExams()){
                    this.validatingTests.add(toDTO(exam));
                }
            }

            else if(course.getValidatingExams()==null){
                this.validatingTests = null;
            }
            if(course.getRejectedExams()!=null){
                for (Exam exam:course.getRejectedExams()){
                    this.rejectedTests.add(toDTO(exam));
                }
            }
            else if(course.getRejectedExams()==null){
                this.rejectedTests = null;
            }
            if(course.getApprovedExams()!=null){
                for (Exam exam:course.getApprovedExams()){
                    this.approvedTests.add(toDTO(exam));
                }
            }
            else if(course.getApprovedExams()==null){
                this.approvedTests = null;
            }

        }
    private ExamDTO toDTO(Exam exam) {

        GradingCriteriaDTO gradingCriteriaDTO = new GradingCriteriaDTO(0, 0);
        if (exam.getGradingCriteria() != null) {
            gradingCriteriaDTO = new GradingCriteriaDTO(
                    exam.getGradingCriteria().getOpenQuestionWeight(),
                    exam.getGradingCriteria().getClosedQuestionWeight()
            );
        }

        List<SubmissionDTO> submissionDTOs = exam.getSubmissions().stream()
                .map(submission -> new SubmissionDTO(submission.getId(), submission.getStatus()))
                .collect(Collectors.toList());


        StatisticsDTO statisticsDTO = new StatisticsDTO(0, 0, 0, 0);
        if (exam.getStatistics() != null) {
            statisticsDTO = new StatisticsDTO(
                    exam.getStatistics().getSubmissionCount(),
                    exam.getStatistics().getPassCount(),
                    exam.getStatistics().getFailCount(),
                    exam.getStatistics().getAverageScore()
            );
        }


        return new ExamDTO(
                exam.getId(),
                getQuestionDTOs(exam.getQuestions()),
                exam.getTotalPoints(),
                exam.getExamMaker(),
                exam.getExamValidator(),
                exam.getValidationStatus(),
                exam.getReason(),
                gradingCriteriaDTO,
                submissionDTOs,
                statisticsDTO

        );
    }
    private List<QuestionResponse> getQuestionDTOs(List<QuestionEntity> questions) {
        List<QuestionResponse> dtos = new ArrayList<>();

        for (QuestionEntity question : questions) {
            dtos.add(new QuestionResponse(question));
        }
        return dtos;
    }
}
