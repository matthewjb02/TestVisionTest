package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;

@Entity
public class Exam {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Student student;

    @OneToOne
    private Test test;

    private State state;

    protected Exam() {
    }

    public Exam(Student student, Test test) {
        this.student = student;
        this.test = test;
        this.state = State.Active;
    }

    public void seeQuestion() {
        test.getQuestions();
    }

    public void answerQuestion(String answer) {
    }

    public Exam endExam() {
        this.state = State.Completed;
        return this;
    }

    public Student getStudent() {
        return student;
    }

    public Test getTest() {
        return test;
    }

    public State getState() {
        return this.state;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
