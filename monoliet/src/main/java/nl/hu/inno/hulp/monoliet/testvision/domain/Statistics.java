package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int submissionCount;
    private int passCount;
    private int failCount;
    private double averageScore;


    public Statistics(Long testId, int submissionCount, int passCount, int failCount, double averageScore) {
        this.id = id;
        this.submissionCount = submissionCount;
        this.passCount = passCount;
        this.failCount = failCount;
        this.averageScore = averageScore;
    }

    public Statistics() {
    }

    public Long getId() {
        return id;
    }

    public int getSubmissionCount() {
        return submissionCount;
    }

    public int getPassCount() {
        return passCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public double getAverageScore() {
        return averageScore;
    }
}