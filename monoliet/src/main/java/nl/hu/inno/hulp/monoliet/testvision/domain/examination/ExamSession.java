package nl.hu.inno.hulp.monoliet.testvision.domain.examination;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.PasswordIncorrectException;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class ExamSession {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    private Exam exam;

    private ExamState state;
    private int duration;
    private int minutesSpent;
    private String password;

    protected ExamSession() {
    }

    public ExamSession(Examination context, ExamState state) {
        this.state = ExamState.Published;
        this.duration = context.totalDuration(student.isExtraTimeRight());
        this.exam = context.getExam();
        this.password = context.getPassword();
    }

    public ExamSession startSession(String password) {
        if (verifyPassword(password)) {
            changeState(ExamState.Active);
            return this;
        }

        throw new PasswordIncorrectException("This password is incorrect.");
    }

    public ExamSession answerQuestion(int questionNr, Object answer) {
        Question question = exam.getQuestions().get(questionNr - 1);

        if (question.getClass().equals(MultipleChoiceQuestion.class)){
            MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;
            mcQuestion.setAnswer((int)answer);
        } else {
            OpenQuestion openQuestion = (OpenQuestion)question;
            openQuestion.setAnswer((String) answer);
        }

        return this;
    }

    public ExamSession endSession() {
        changeState(ExamState.Completed);
        return this;
    }

    public boolean verifyPassword(String inputPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(inputPassword, password);
    }

    public void changeState(ExamState state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }
}