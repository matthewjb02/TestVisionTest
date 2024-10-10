package nl.hu.inno.hulp.examination.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import nl.hu.inno.hulp.commons.enums.ExamState;
import nl.hu.inno.hulp.commons.exception.PasswordIncorrectException;

import java.util.List;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
public class ExamSession {
    @Id
    @GeneratedValue
    private Long id;

    private Long examinationId;

    @Transient
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
        //this.securedPassword = hashPassword(context.getPassword());
        this.securedPassword = context.getPassword();
        this.examDate = context.getExamDate();
        this.studentId = studentId;
    }

    public ExamSession startSession(String password) {
        if (verifyPassword(password)) {
            changeState(ExamState.Active);
            return this;
        }

        throw new PasswordIncorrectException("This password is incorrect.");
    }

    public ExamSession answerQuestion(int questionNr, Object answer) {
        QuestionEntity question = seeQuestion(questionNr);

        if (question.getClass().equals(MultipleChoiceQuestion.class)){
            MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;
            mcQuestion.setGivenAnswers((List<Integer>)answer);
        } else {
            OpenQuestion openQuestion = (OpenQuestion)question;
            openQuestion.setAnswer((String) answer);
        }

        return this;
    }

    public QuestionEntity seeQuestion(int questionNr) {
        return exam.getQuestions().get(questionNr - 1);
    }

    public ExamSession endSession() {
        changeState(ExamState.Completed);
        return this;
    }

    /*public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }*/

    public boolean verifyPassword(String inputPassword) {
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //return encoder.matches(inputPassword, securedPassword);
        return securedPassword.equals(inputPassword); //security will be implemented after splitting up in smaller applications
    }

    public void changeState(ExamState state) {
        this.state = state;
    }
}