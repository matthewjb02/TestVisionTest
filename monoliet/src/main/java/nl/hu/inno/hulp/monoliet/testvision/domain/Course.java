package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany
    private List<Test> approvedTests=new ArrayList<>();
    @OneToMany
    private List<Test> validatingTests=new ArrayList<>();
    @OneToMany
    private List<Test> rejectedTests=new ArrayList<>();

    public Course(){

    }

    public Course(String name){
        this.name = name;
    }

    public void addTest(Test test){
        if (test.getValidationStatus().equals(Validation.APPROVED)){
        approvedTests.add(test);}
        else if(test.getValidationStatus().equals(Validation.WAITING)){
            validatingTests.add(test);
        } else if (test.getValidationStatus().equals(Validation.DENIED) ) {
            rejectedTests.add(test);
        }
    }

    public List<Test> getApprovedTests(){
        return approvedTests;
    }

    public List<Test> getValidatingTests() {
        return validatingTests;
    }
    public List<Test> getRejectedTests(){
        return rejectedTests;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
