package nl.hu.inno.hulp.monoliet.testvision.domain.submission;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;

@Entity
public class Grading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double grade = 0.0;
    private String comments;

    @OneToOne
    private Teacher grader;

    public Grading(double grade, String comments) {
        this.grade = grade;
        this.comments = comments;
    }

    protected Grading() {}

    public static Grading createGrading(double grade, String comments) {
        return new Grading(grade, comments);
    }

    public Long getId() {
        return id;
    }

    public void setGrader(Teacher grader) {
        this.grader = grader;

    }

    public int getGrade() {
        return (int) grade;
    }

    public String getComments() {
        return comments;
    }
}