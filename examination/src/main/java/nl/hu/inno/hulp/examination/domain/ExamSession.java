package nl.hu.inno.hulp.examination.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import nl.hu.inno.hulp.commons.enums.ExamState;
import nl.hu.inno.hulp.commons.exception.ExamDateException;
import nl.hu.inno.hulp.commons.exception.PasswordIncorrectException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
public class ExamSession {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "examination_id")
    private Long examinationId;

    @Column(name = "student_id")
    private Long studentId;

    @Transient
    private Long examId;

    @Embedded
    private ExamDate examDate;

    private ExamState state;
    private int duration;

    @Getter(AccessLevel.NONE)
    private String securedPassword;

    protected ExamSession() {
    }

    public ExamSession(Examination context, Long studentId, boolean extraTimeRight) {
        this.state = ExamState.Published;
        this.examinationId = context.getId();
        this.duration = context.totalDuration(extraTimeRight);
        this.examId = context.getExamId();
        this.securedPassword = hashPassword(context.getPassword());
        this.examDate = context.getExamDate();
        this.studentId = studentId;
    }

    public ExamSession startSession(String password) {
//        if (!examDate.checkDate()) {
//            throw new ExamDateException("Session cannot be started because your to soon or to late");
//        }

        if (verifyPassword(password)) {
            changeState(ExamState.Active);
            return this;
        }

        throw new PasswordIncorrectException("This password is incorrect.");
    }

    /*public ExamSession answerQuestion(int questionNr, Object answer) {
        QuestionEntity question = seeQuestion(questionNr);

        if (question.getClass().equals(MultipleChoiceQuestion.class)){
            MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;
            mcQuestion.setGivenAnswers((List<Integer>)answer);
        } else {
            OpenQuestion openQuestion = (OpenQuestion)question;
            openQuestion.setAnswer((String) answer);
        }

        return this;
    }*/

    public ExamSession endSession() {
        changeState(ExamState.Completed);
        return this;
    }

    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public boolean verifyPassword(String inputPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(inputPassword, securedPassword);
    }

    public void changeState(ExamState state) {
        this.state = state;
    }
}