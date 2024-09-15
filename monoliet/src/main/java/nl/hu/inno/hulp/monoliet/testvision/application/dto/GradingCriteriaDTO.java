package nl.hu.inno.hulp.monoliet.testvision.application.dto;

public class GradingCriteriaDTO {
    private double openQuestionWeight;
    private double closedQuestionWeight;

    public GradingCriteriaDTO(double openQuestionWeight, double closedQuestionWeight) {
        this.openQuestionWeight = openQuestionWeight;
        this.closedQuestionWeight = closedQuestionWeight;
    }

    public double getOpenQuestionWeight() {
        return openQuestionWeight;
    }

    public double getClosedQuestionWeight() {
        return closedQuestionWeight;
    }
}