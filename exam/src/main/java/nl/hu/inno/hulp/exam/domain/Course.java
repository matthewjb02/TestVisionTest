package nl.hu.inno.hulp.exam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import nl.hu.inno.hulp.commons.enums.ValidationStatus;
import nl.hu.inno.hulp.exam.domain.question.QuestionEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Scope;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document
@Scope("course")
@Collection("course")
@Getter
public class Course {
    @Id
    private String id;
    @Field
    private String name;
    @Field
    private List<String> teacherIds =new ArrayList<>();
    @Field
    private List<Exam> approvedExams =new ArrayList<>();
    @Field
    private List<Exam> validatingExams =new ArrayList<>();
    @Field
    private List<Exam> rejectedExams =new ArrayList<>();

    protected Course(){

    }

    public Course(String name){
        this.name = name;
        this.id= UUID.randomUUID().toString();
    }

    public void addTeacher(String teacherId){
        teacherIds.add(teacherId);
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

        if (this.getTeacherIds().contains(exam.getExamMakerId()) && exam.getExamMakerId()!=exam.getExamValidatorId() ||
                this.getTeacherIds().contains(exam.getExamValidatorId())&&exam.getExamValidatorId() != exam.getExamMakerId()) {
            return true;
        } else throw new Exception("The Teacher does not teach this course");
    }

    private boolean canIApproveThisExam(String examValidatorId, Exam exam) throws Exception {
        if (getValidatingExams().contains(exam) && exam.getExamValidatorId().equals(examValidatorId)){
            return true;
        }
        else if(!exam.getExamValidatorId().equals(examValidatorId) && getValidatingExams().contains(exam)){
            throw new Exception("The Teacher is not assigned as validator, but the exam needs to be Validated");
        }
        else
            throw new Exception("The exam cannot be validated");
    }

    public void approveExam(Exam exam, String  examValidatorId) throws Exception {
        if (doesTeacherTeachCourse(exam)){
            exam.setValidationStatus(ValidationStatus.APPROVED);
            this.getValidatingExams().remove(exam);
            this.getApprovedExams().add(exam);
        }
    }

    public void rejectExam(Exam exam, String  examValidatorId, String reason) throws Exception {
        if (doesTeacherTeachCourse(exam)){
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
