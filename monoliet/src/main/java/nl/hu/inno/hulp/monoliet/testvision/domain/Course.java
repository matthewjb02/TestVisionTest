package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.ValidationStatus;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    @ManyToMany
    private List<Teacher> teachers;

    @OneToMany
    private List<Exam> approvedExams =new ArrayList<>();
    @OneToMany
    private List<Exam> validatingExams =new ArrayList<>();
    @OneToMany
    private List<Exam> rejectedExams =new ArrayList<>();

    public Course(){

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

    public List<Exam> getApprovedExams(){
        return approvedExams;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<Exam> getValidatingExams() {
        return validatingExams;
    }
    public List<Exam> getRejectedExams(){
        return rejectedExams;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
