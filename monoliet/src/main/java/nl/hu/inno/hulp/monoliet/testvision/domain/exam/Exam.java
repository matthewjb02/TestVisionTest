package nl.hu.inno.hulp.monoliet.testvision.domain.exam;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter

public class Exam {
    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    private GradingCriteria gradingCriteria;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Submission> submissions = new ArrayList<>();

    private ValidationStatus validationStatus= ValidationStatus.WAITING;
    private String reason;
  
    @OneToMany
    private List<Question> questions;
    @OneToOne
    private Teacher examValidator;

    @OneToOne(cascade = CascadeType.ALL)
    private Statistics statistics;
    @OneToOne
    private Teacher examMaker;
    private int totalPoints;
    protected Exam(){

    }

    public Exam(Course course, Teacher examMaker, Teacher examValidator, Question... questions){
        if (questions.length > 0){
            this.questions = new ArrayList<>(Arrays.asList(questions));
            this.examMaker = examMaker;
            this.examValidator = examValidator;
            calculateTotalPoints();
        }
    }

    public void calculateTotalPoints(){
        if (questions == null){
            //TODO: Throw error
            return;
        }
        totalPoints = questions.stream().mapToInt(Question::getPoints).sum();
    }

    public void removeQuestions(List<Question> questions){
        this.questions.removeAll(questions);
    }

    public void addQuestions(List<Question> questions){
        this.questions.addAll(questions);
    }

    public Teacher getExamValidatorMail() {
        return examValidator;
    }

    public Teacher getMakerMail() {
        return examMaker;
    }

    public List<String> getQuestionsAsString() {
        return questions.stream()
                .map(Question::getQuestion)
                .collect(Collectors.toList());
    }

    public void updateStatistics() {
        double passGrade = 5.5;

        int submissionCount = submissions.size();

        int passCount = (int) submissions.stream()
                .filter(submission -> submission.calculateGrade() >= passGrade)
                .count();

        int failCount = submissionCount - passCount;

        double averageScore = submissions.stream()
                .mapToDouble(Submission::calculateGrade)
                .average()
                .orElse(0);

        statistics = new Statistics(submissionCount, passCount, failCount, averageScore);
    }

    public int getTotalOpenQuestionPoints(){
        return questions.stream()
                .filter(question -> question instanceof OpenQuestion)
                .mapToInt(Question::getPoints)
                .sum();
    }

    public int getTotalMultipleChoiceQuestionPoints(){
       return questions.stream()
                .filter(question -> question instanceof MultipleChoiceQuestion)
                .mapToInt(Question::getPoints)
                .sum();
    }

    public void addExamMaker(Teacher examMaker) {
        this.examMaker = examMaker;
    }
    public void addExamValidator(Teacher examValidator) {
        this.examValidator = examValidator;
    }
    public void addGradingCriteria(GradingCriteria gradingCriteria) {
        this.gradingCriteria = gradingCriteria;
    }

    public void addSubmission(Submission submission) {
        this.submissions.add(submission);
    }
}
