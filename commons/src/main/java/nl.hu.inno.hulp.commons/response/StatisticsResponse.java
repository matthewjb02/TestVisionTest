package nl.hu.inno.hulp.commons.response;

import lombok.Getter;

@Getter
public class StatisticsResponse {
    private int submissionCount;
    private int passCount;
    private int failCount;
    private double averageScore;

    public StatisticsResponse(int submissionCount, int passCount, int failCount, double averageScore) {
        this.submissionCount = submissionCount;
        this.passCount = passCount;
        this.failCount = failCount;
        this.averageScore = averageScore;
    }
}
