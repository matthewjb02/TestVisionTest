package nl.hu.inno.hulp.monoliet.testvision.domain.examination;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;

@Entity
public class Examination {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    private Exam exam;

    private State state;

    protected Examination() {
    }

    public Examination(Student student, Exam exam) {
        this.student = student;
        this.exam = exam;
        this.state = State.Active;
    }

    public Question seeQuestion(int nr) {
        return exam.getQuestions().get(nr - 1);
    }

    public void answerQuestion(int questionNr, Object answer) {
        Question question = seeQuestion(questionNr);

        System.out.println(question.getClass());

        if (question.getClass().equals(MultipleChoiceQuestion.class)){
            MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;
            mcQuestion.setAnswer((int)answer);
        } else {
            OpenQuestion openQuestion = (OpenQuestion)question;
            openQuestion.setAnswer((String) answer);
        }
    }

    public Examination endExam() {
        this.state = State.Completed;
        return this;
    }

    public Student getStudent() {
        return student;
    }

    public Exam getExam() {
        return exam;
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

    public Long getStudentId() {
        return student.getId();
    }
}
