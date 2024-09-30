package nl.hu.inno.hulp.monoliet.testvision.domain.examination;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.PasswordIncorrectException;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
public class ExamSession {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    private Exam exam;

    @Embedded
    private ExamDate examDate;

    private ExamState state;
    private int duration;
    private int minutesSpent;

    @Getter(AccessLevel.NONE)
    private String securedPassword;

    protected ExamSession() {
    }

    public ExamSession(Examination context, Student student) {
        this.state = ExamState.Published;
        this.duration = context.totalDuration(student.isExtraTimeRight());
        this.exam = context.getExam();
        this.securedPassword = context.getPassword();
    }

    public ExamSession startSession(String password) {
        if (verifyPassword(password)) {
            changeState(ExamState.Active);
            return this;
        }

        throw new PasswordIncorrectException("This password is incorrect.");
    }

    public ExamSession answerQuestion(int questionNr, Object answer) {
        Question question = seeQuestion(questionNr);

        if (question.getClass().equals(MultipleChoiceQuestion.class)){
            MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;
            mcQuestion.setAnswer((int)answer);
        } else {
            OpenQuestion openQuestion = (OpenQuestion)question;
            openQuestion.setAnswer((String) answer);
        }

        return this;
    }

    public Question seeQuestion(int questionNr) {
        return exam.getQuestions().get(questionNr - 1);
    }

    public ExamSession endSession() {
        changeState(ExamState.Completed);
        return this;
    }

    public boolean verifyPassword(String inputPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(inputPassword, securedPassword);
    }

    public void changeState(ExamState state) {
        this.state = state;
    }

    public Long getStudentId() {
        return student.getId();
    }
}