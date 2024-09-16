package nl.hu.inno.hulp.monoliet.testvision.domain;

import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.test.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestTest {

    private Test test;
    private Question question1;
    private Question question2;

    @BeforeEach
    void createTest() {
        question1 = new Question(5, "Question 1");
        question2 = new Question(10, "Question 2");
        test = new Test("maker@example.com", "validator@example.com", question1, question2);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Correctly calculated total points")
    void testCalculateTotalPoints() {
        assertEquals(15, test.getTotalPoints());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Questions can be added and removed")
    void testAddAndRemoveQuestions() {
        Question question3 = new Question(20, "Question 3");

        test.addQuestions(List.of(question3));
        assertEquals(3, test.getQuestions().size());

        test.removeQuestions(List.of(question3));
        assertEquals(2, test.getQuestions().size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Validations are set correctly")
    void testSetAndGetValidationStatus() {
        assertEquals(Validation.WAITING, test.getValidationStatus());

        test.setValidationStatus(Validation.APPROVED);
        assertEquals(Validation.APPROVED, test.getValidationStatus());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("You can add grading criteria")
    void testAddGradingCriteria() {
        GradingCriteria criteria = new GradingCriteria();
        test.addGradingCriteria(criteria);
        assertEquals(criteria, test.getGradingCriteria());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("You can add submissions")
    void testAddSubmission() {
        Submission submission = new Submission();
        test.addSubmission(submission);
        assertEquals(1, test.getSubmissions().size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("You can add statistics")
    void testAddStatistics() {
        Statistics stats = new Statistics( 1, 1, 0, 1);
        test.addStatistics(stats);
        assertEquals(stats, test.getStatistics());
    }
}
