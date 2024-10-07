package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Statistics;

public class StatisticsResponse {
    private int submissionCount;
    private int passCount;
    private int failCount;
    private double averageScore;

    public StatisticsResponse(Statistics statistics) {
        this.submissionCount = statistics.getSubmissionCount();
        this.passCount = statistics.getPassCount();
        this.failCount = statistics.getFailCount();
        this.averageScore = statistics.getAverageScore();
    }
}
