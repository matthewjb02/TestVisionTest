package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;

@Entity
public class Exam {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    private Test test;

    private State state;

    protected Exam() {
    }

    public Exam(Student student, Test test) {
        this.student = student;
        this.test = test;
        this.state = State.Active;
    }

    public Question seeQuestion(int nr) {
        return test.getQuestions().get(nr - 1);
    }

    public void answerQuestion(int questionNr, String answer) {
        Question question = seeQuestion(questionNr);
        question.setAnswer(answer);
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
