package nl.hu.inno.hulp.monoliet.testvision.domain.exam;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Exam {
    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    private GradingCriteria gradingCriteria;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Submission> submissions = new ArrayList<>();

    private Validation validationStatus= Validation.WAITING;
    private String reason;
  
    @OneToMany
    private List<Question> questions;
    private String examValidatorMail;

    @OneToOne(cascade = CascadeType.ALL)
    private Statistics statistics;

    private String makerMail;
    private int totalPoints;

    public Exam(){

    }

    public Exam(String makerMail, String examValidatorMail, Question... questions){
        if (questions.length > 0){
            this.questions = new ArrayList<>(Arrays.asList(questions));
            this.makerMail = makerMail;
            this.examValidatorMail = examValidatorMail;
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

    public int getTotalPoints(){
        return  totalPoints;
    }

    public Long getId(){
        return id;
    }

    public List<Question> getQuestions(){
        return questions;
    }

    public void removeQuestions(List<Question> questions){
        this.questions.removeAll(questions);
    }

    public void addQuestions(List<Question> questions){
        this.questions.addAll(questions);
    }

    public String getExamValidatorMail() {
        return examValidatorMail;
    }

    public String getMakerMail() {
        return makerMail;
    }


    public Validation getValidationStatus() {
        return validationStatus;
    }

    public String getReason() {
        return reason;
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



    public void addGradingCriteria(GradingCriteria gradingCriteria) {
        this.gradingCriteria = gradingCriteria;
    }

    public void addSubmission(Submission submission) {
        this.submissions.add(submission);
    }

    public GradingCriteria getGradingCriteria() {
        return gradingCriteria;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void addStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public void setExamValidatorMail(String examValidator) {
        this.examValidatorMail = examValidator;
    }

    public void setMakerMail(String maker) {
        this.makerMail = maker;
    }

    public void setValidationStatus(Validation validationStatus) {
        this.validationStatus = validationStatus;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
