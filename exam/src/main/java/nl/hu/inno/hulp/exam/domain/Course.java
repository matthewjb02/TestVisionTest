package nl.hu.inno.hulp.exam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.ValidationStatus;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    private List<Teacher> teachers=new ArrayList<>();
    @OneToMany
    private List<Exam> approvedExams =new ArrayList<>();
    @OneToMany
    private List<Exam> validatingExams =new ArrayList<>();
    @OneToMany
    private List<Exam> rejectedExams =new ArrayList<>();

    protected Course(){

    }

    public Course(String name){
        this.name = name;
    }
    public void addTeacher(Teacher teacher){
        teachers.add(teacher);
    }
    public void addExam(Exam exam){
        if (exam.getValidationStatus().equals(ValidationStatus.APPROVED)){
        approvedExams.add(exam);}
        else if(exam.getValidationStatus().equals(ValidationStatus.WAITING)){
            validatingExams.add(exam);
        } else if (exam.getValidationStatus().equals(ValidationStatus.DENIED) ) {
            rejectedExams.add(exam);
        }
    }
    private boolean doesTeacherTeachCourse(Exam exam) throws Exception {

        if (this.getTeachers().contains(exam.getExamMaker())&&exam.getExamMaker()!=exam.getExamValidator()||this.getTeachers().contains(exam.getExamValidator())&&exam.getExamValidator()!=exam.getExamMaker()) {
            return true;
        } else throw new Exception("The Teacher does not teach this course");
    }

    private boolean canIApproveThisExam(Teacher examValidator,Exam exam) throws Exception {
        if (this.getValidatingExams().contains(exam)&&exam.getExamValidator() ==examValidator){
            return true;
        }
        else if(exam.getExamValidator() !=examValidator&&this.getValidatingExams().contains(exam)){
            throw new Exception("The Teacher is not assigned as validator, but the exam needs to be Validated");
        }
        else throw new Exception("The exam cannot be validated");
    }



    public void approveExam(Exam exam,Teacher examValidator) throws Exception {
        if (doesTeacherTeachCourse(exam)&& canIApproveThisExam(examValidator,exam)){
            exam.setValidationStatus(ValidationStatus.APPROVED);
            this.getValidatingExams().remove(exam);
            this.getApprovedExams().add(exam);
        }
    }
    public void rejectExam(Exam exam,Teacher examValidator,String reason) throws Exception {
        if (doesTeacherTeachCourse(exam)&& canIApproveThisExam(examValidator,exam)){
            exam.setValidationStatus(ValidationStatus.DENIED);
            this.getValidatingExams().remove(exam);
            this.getRejectedExams().add(exam);
            exam.setReason(reason);
        }
    }
    public void viewWrongExam(Exam exam) throws Exception {
        if (this.getRejectedExams().contains(exam)){
            System.out.println(exam.getReason());
        }
        else throw new Exception("This exam was not rejected");
    }
    public void modifyQuestions(Exam exam, List<QuestionEntity> oldQuestions, List<QuestionEntity> newQuestion) {
        if (this.getRejectedExams().contains(exam)){
            exam.removeQuestions(oldQuestions);
            exam.addQuestions(newQuestion);
            this.getValidatingExams().add(exam);
            this.getRejectedExams().remove(exam);
            exam.setValidationStatus(ValidationStatus.WAITING);
            exam.setReason("");
        }
    }


}
