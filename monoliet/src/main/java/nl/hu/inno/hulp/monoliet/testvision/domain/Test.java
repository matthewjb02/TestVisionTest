package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
public class Test {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Question> questions;
    @ManyToOne
    protected Teacher testValidator;
    @ManyToOne
    protected Teacher Maker;
    private int totalPoints;
    protected Validation validation = Validation.WAITING;
    protected String reason;

    public Test(){

    }

    protected Test(Question... questions){
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

    protected List<Question> getQuestions() {
        return questions;
    }


}

