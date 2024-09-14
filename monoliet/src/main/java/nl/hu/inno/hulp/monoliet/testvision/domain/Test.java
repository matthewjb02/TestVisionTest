package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Test {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Question> questions = new ArrayList<>();

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

    public Question getQuestion(int nr) {
        return questions.get(nr - 1);
    }

    /*public List<String> getQuestionsAsString(){
        return questions.stream()
                .map(Question::getQuestion)
                .collect(Collectors.toList());
    }*/
}
