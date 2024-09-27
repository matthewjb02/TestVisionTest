package nl.hu.inno.hulp.monoliet.testvision.domain.exam;

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


    public Statistics(int submissionCount, int passCount, int failCount, double averageScore) {
        this.submissionCount = submissionCount;
        this.passCount = passCount;
        this.failCount = failCount;
        this.averageScore = averageScore;
    }

    public Statistics() {
    }

    public static Statistics createStatistics(int submissionCount, int passCount, int failCount, double averageScore) {
        return new Statistics(submissionCount, passCount, failCount, averageScore);
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