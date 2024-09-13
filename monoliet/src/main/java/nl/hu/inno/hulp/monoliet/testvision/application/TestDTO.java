package nl.hu.inno.hulp.monoliet.testvision.application;

import java.util.List;

public class TestDTO {

    private Long id;
    private List<String> questions;
    private int totalPoints;

    public TestDTO(){

    }

    public TestDTO(Long id, List<String> questions, int totalPoints) {
        this.id = id;
        this.questions = questions;
        this.totalPoints = totalPoints;
    }

    public Long getId() {
        return id;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public int getTotalPoints() {
        return totalPoints;
    }
}
