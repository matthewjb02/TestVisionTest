package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Validation;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

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

    public void addExam(Exam exam){
        if (exam.getValidationStatus().equals(Validation.APPROVED)){
        approvedExams.add(exam);}
        else if(exam.getValidationStatus().equals(Validation.WAITING)){
            validatingExams.add(exam);
        } else if (exam.getValidationStatus().equals(Validation.DENIED) ) {
            rejectedExams.add(exam);
        }
    }

    public List<Exam> getApprovedExams(){
        return approvedExams;
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
