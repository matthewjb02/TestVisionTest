package nl.hu.inno.hulp.monoliet.testvision.domain.submission;

import jakarta.persistence.*;

@Entity
public class Grading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double grade = 0.0;
    private String comments;


    public Grading(double grade, String comments) {
        this.grade = grade;
        this.comments = comments;
    }

    public Grading() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getGrade() {
        return grade;
    }

    public String getComments() {
        return comments;
    }
}