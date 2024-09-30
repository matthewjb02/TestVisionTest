package nl.hu.inno.hulp.monoliet.testvision.domain.exam;

import jakarta.persistence.*;
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
    @Transient
    private Course  course;
    public Exam(){

    }

    public Exam(Course course, Teacher examMaker, Teacher examValidator, Question... questions){
        if (questions.length > 0){
            this.questions = new ArrayList<>(Arrays.asList(questions));
            this.examMaker = examMaker;
            this.examValidator = examValidator;
            this.course = course;
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
    private boolean doesTeacherTeachCourse() throws Exception {

        if (this.course.getTeachers().contains(this.examMaker)&&this.examMaker!=this.examValidator||this.course.getTeachers().contains(this.examValidator)&&this.examMaker!=this.examValidator) {
        return true;
        } else throw new Exception("The Teacher does not teach this course");
    }

    private boolean canIApproveThisExam(Teacher examValidator) throws Exception {
        if (course.getValidatingExams().contains(this)&&this.examValidator ==examValidator){
            return true;
        }
        else if(this.examValidator !=examValidator&&course.getValidatingExams().contains(this)){
            throw new Exception("The Teacher is not assigned as validator, but the exam needs to be Validated");
        }
        else throw new Exception("The exam cannot be validated");
    }



    public void approveExam() throws Exception {
        if (doesTeacherTeachCourse()&& canIApproveThisExam(this.examValidator)){
            this.setValidationStatus(ValidationStatus.APPROVED);
            course.getValidatingExams().remove(this);
            course.getApprovedExams().add(this);
        }
    }
    public void rejectExam(String reason) throws Exception {
        if (doesTeacherTeachCourse()&& canIApproveThisExam(this.examValidator)) {
            this.setValidationStatus(ValidationStatus.DENIED);
            course.getValidatingExams().remove(this);
            course.getRejectedExams().add(this);
            this.setReason(reason);
        }
    }
    public void viewWrongExam() throws Exception {
        if (Objects.equals(this.getMakerMail(), this.examMaker.getEmail().getEmailString()) &&course.getRejectedExams().contains(this)){
            System.out.println(this.getReason());
        }
        else throw new Exception("This exam was not rejected");
    }
    public void modifyQuestions(List<Question> oldQuestions, List<Question> newQuestion) {
        if (Objects.equals(this.getMakerMail(), this.examMaker.getEmail().getEmailString()) &&course.getRejectedExams().contains(this)){
            this.removeQuestions(oldQuestions);
            this.addQuestions(newQuestion);
            course.getValidatingExams().add(this);
            course.getRejectedExams().remove(this);
            this.setValidationStatus(ValidationStatus.WAITING);
            this.setReason("");
        }
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

    public Teacher getExamValidatorMail() {
        return examValidator;
    }

    public Teacher getMakerMail() {
        return examMaker;
    }


    public ValidationStatus getValidationStatus() {
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

    public void addExamMaker(Teacher examMaker) {
        this.examMaker = examMaker;
    }
    public void addExamValidator(Teacher examValidator) {
        this.examValidator = examValidator;
    }
    public void addCourse(Course course) {
        this.course = course;
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


    private void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
