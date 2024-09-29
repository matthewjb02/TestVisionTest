package nl.hu.inno.hulp.monoliet.testvision.domain.examination;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Entity
public class Examination {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Student student;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Student> candidates;

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

    public boolean validateExtraTimeRight() {
        return false;
    }

    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public void changePassword(String newPassword) {
        this.password = hashPassword(newPassword);
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Exam getExam() {
        return exam;
    }

    public ExamState getState() {
        return this.state;
    }

    public String getPassword() {
        return this.password;
    }

    public Long getStudentId() {
        return student.getId();
    }
}
