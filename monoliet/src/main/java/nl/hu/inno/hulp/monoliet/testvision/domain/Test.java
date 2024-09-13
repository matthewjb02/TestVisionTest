package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Test {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Question> questions;

    private int totalPoints;

    public Test(){

    }

    public Test(Question... questions){
        this.questions = Arrays.asList(questions);
        calculateTotalPoints();
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

    public List<String> getQuestionsAsString(){
        return questions.stream()
                .map(Question::getQuestion)
                .collect(Collectors.toList());
    }
}
