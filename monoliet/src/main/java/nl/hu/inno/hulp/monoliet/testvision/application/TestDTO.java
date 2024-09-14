package nl.hu.inno.hulp.monoliet.testvision.application;

import nl.hu.inno.hulp.monoliet.testvision.domain.Question;

import java.util.List;

public class TestDTO {

    private Long id;
    private List<Question> questions;
    private int totalPoints;

    public TestDTO(){

    }

    public TestDTO(Long id, List<Question> questions, int totalPoints) {
        this.id = id;
        this.questions = questions;
        this.totalPoints = totalPoints;
    }

    public Long getId() {
        return id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getTotalPoints() {
        return totalPoints;
    }
}
