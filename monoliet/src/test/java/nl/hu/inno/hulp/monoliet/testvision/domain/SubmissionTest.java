package nl.hu.inno.hulp.monoliet.testvision.domain;

import nl.hu.inno.hulp.monoliet.testvision.domain.examination.Examination;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Grading;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.SubmissionStatus;
import nl.hu.inno.hulp.monoliet.testvision.domain.test.GradingCriteria;
import nl.hu.inno.hulp.monoliet.testvision.domain.test.Test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubmissionTest {

    @ParameterizedTest(name = "{index} => totalPoints={0}, givenPointsOpen={1}, givenPointsMC={2}, expectedGrade={3}")
    @MethodSource("provideTestData")
    @DisplayName("Parameterized test for calculateGrade")
    void testCalculateGrade(int totalExamPoints, int totalOpenPoints, int totalMcPoints, int givenPointsOpen, int givenPointsMC, double expectedGrade) {

        Examination examination = mock(Examination.class);
        Test test = mock(Test.class);
        OpenQuestion openQuestion = mock(OpenQuestion.class);
        MultipleChoiceQuestion mcQuestion = mock(MultipleChoiceQuestion.class);
        GradingCriteria gradingCriteria = new GradingCriteria(0.5, 0.5);

        when(test.getGradingCriteria()).thenReturn(gradingCriteria);
        when(test.getQuestions()).thenReturn(List.of(openQuestion, mcQuestion));
        when(test.getTotalPoints()).thenReturn(totalExamPoints);
        when(test.getTotalMultipleChoiceQuestionPoints()).thenReturn(totalMcPoints);
        when(test.getTotalOpenQuestionPoints()).thenReturn(totalOpenPoints);
        when(examination.getTest()).thenReturn(test);

        Submission submission = new Submission(examination);

        when(submission.calculateTotalOpenGivenPoints()).thenReturn(givenPointsOpen);
        when(submission.calculateTotalMultipleChoiceGivenPoints()).thenReturn(givenPointsMC);


        double grade = submission.calculateGrade();

        assertEquals(expectedGrade, grade);
    }

    private static Stream<Arguments> provideTestData() {
        return Stream.of(
                // totalPoints, totalOpenPoints, totalMcPoints, givenPointsOpen, givenPointsMC, expectedGrade
                Arguments.of(10, 5, 5, 5, 5, 10.0),
                Arguments.of(20, 10, 10, 5, 5, 5.0),
                Arguments.of(20, 10, 10, 6, 5, 5.5),
                Arguments.of(0, 0, 0, 0, 0, 1.0)


        );
    }

    @org.junit.jupiter.api.Test
    @DisplayName("When a test is sumbitted the status of the submission is SUBMITTED")
    void testSubmissionState() {
        Examination examination = mock(Examination.class);
        Submission submission = new Submission(examination);

        assertEquals(SubmissionStatus.SUBMITTED, submission.getStatus());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("When a test is graded the status of the submission is GRADED")
    void testGradedSubmissionState() {
        Examination examination = mock(Examination.class);
        Submission submission = new Submission(examination);

        submission.addGrading(new Grading());

        assertEquals(SubmissionStatus.GRADED, submission.getStatus());

    }








}

