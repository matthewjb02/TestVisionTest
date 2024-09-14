package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestStatistics {

    @Id
    private Long id;

    private int totalScore;
    private int totalAttempts;
    private int averageScore;

    public TestStatistics(int totalScore, int totalAttempts, int averageScore) {
        this.totalScore = totalScore;
        this.totalAttempts = totalAttempts;
        this.averageScore = averageScore;
    }

    public TestStatistics() {

    }
}
