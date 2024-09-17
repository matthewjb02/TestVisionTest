package nl.hu.inno.hulp.monoliet.testvision.domain.test;

import jakarta.persistence.Embeddable;

@Embeddable
public class GradingCriteria {

    private double openQuestionWeight;
    private double closedQuestionWeight;

    public GradingCriteria(double openQuestionWeight, double closedQuestionWeight) {
        if (openQuestionWeight + closedQuestionWeight != 1.0 || openQuestionWeight < 0 || closedQuestionWeight < 0) {
            throw new IllegalArgumentException("The sum of the weights must be 1.0 and both weights must be positive");
        }

        this.openQuestionWeight = openQuestionWeight;
        this.closedQuestionWeight = closedQuestionWeight;
    }

    public GradingCriteria() {

    }

    public double calculateWeightedScore(int openQuestionScore, int closedQuestionScore) {
        return (openQuestionScore * openQuestionWeight) + (closedQuestionScore * closedQuestionWeight);
    }

    public double getOpenQuestionWeight() {
        return openQuestionWeight;
    }

    public double getClosedQuestionWeight() {
        return closedQuestionWeight;
    }


}