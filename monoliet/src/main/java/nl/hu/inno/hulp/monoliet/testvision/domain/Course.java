package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue
    private Long id;
    private int year;
    private int semester;
    private String name;
    @ManyToMany(mappedBy = "courses")
    private List<Teacher> teacher;

    @OneToMany
    private List<Test> approvedTests;
    @OneToMany
    private List<Test> validatingTests;
    @OneToMany
    private List<Test> rejectedTests;

    public Course(){

    }

    public Course(String name, int year, int semester){
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.approvedTests = new ArrayList<>();
        this.validatingTests = new ArrayList<>();
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
}
