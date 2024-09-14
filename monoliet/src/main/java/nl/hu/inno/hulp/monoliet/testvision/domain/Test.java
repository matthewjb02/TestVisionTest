package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Test {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private GradingCriteria gradingCriteria;

    @OneToMany
    private List<Submission> submissions = new ArrayList<>();

    @OneToMany
    private List<Question> questions = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Statistics statistics;

    private int totalPoints;

    public Test(){

    }

    public Test(Question... questions){
        if (questions.length > 0){
            this.questions = Arrays.asList(questions);
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

    public List<String> getQuestionsAsString(){
        return questions.stream()
                .map(Question::getQuestion)
                .collect(Collectors.toList());
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
}
