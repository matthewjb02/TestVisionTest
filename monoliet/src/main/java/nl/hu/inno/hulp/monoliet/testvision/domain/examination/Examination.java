package nl.hu.inno.hulp.monoliet.testvision.domain.examination;

import jakarta.persistence.*;
import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Entity
@Getter
public class Examination {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Student student;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Student> candidates;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ExamSession> examSessions;

    @OneToOne(cascade = CascadeType.ALL)
    private Exam exam;

    private ExamState state;

    private String password;

    @Embedded
    private ExamDate examDate;

    private int duration;
    private int extraTime;

    protected Examination() {
    }

    public Examination(Student student, Exam exam) {
        this.student = student;
        this.exam = exam;
        this.state = ExamState.Active;
    }

    public Examination(Exam exam, String password, ExamDate examDate, int duration, int extraTime) {
        this.exam = exam;
        this.password = password;
        this.examDate = examDate;
        this.duration = duration;
        this.extraTime = extraTime;
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
        this.state = ExamState.Completed;
        return this;
    }

    public int totalDuration(boolean extraTimeRight) {
        if (extraTimeRight) {
            return duration + extraTime;
        }

        return duration;
    }

    public List<ExamSession> setupExamSessions() {
        for (Student student : candidates) {
            ExamSession examSession = new ExamSession(this, student);
            storeExamSession(examSession);
        }

        return examSessions;
    }

    public void storeExamSession(ExamSession examSession) {
        examSessions.add(examSession);
    }

    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public Long getStudentId() {
        return student.getId();
    }
}
