
package nl.hu.inno.hulp.monoliet.testvision.application.dto;

public class StatisticsDTO {

    private Long id;
    private int submissionCount;
    private int passCount;
    private int failCount;
    private double averageScore;

    public StatisticsDTO(Long id, int submissionCount, int passCount, int failCount, double averageScore) {
        this.submissionCount = submissionCount;
        this.passCount = passCount;
        this.failCount = failCount;
        this.averageScore = averageScore;
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

    public Long getId() {
        return id;
    }
}