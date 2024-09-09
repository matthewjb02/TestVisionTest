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
    private List<Test> tests;

    public Course(){

    }

    public Course(String name){
        this.name = name;
        this.tests = new ArrayList<>();
    }

    public void addTest(Test test){
        tests.add(test);
    }

    public List<Test> getTests(){
        return tests;
    }
}
