package nl.hu.inno.hulp.monoliet.testvision.domain;

import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Grading;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.SubmissionStatus;
import nl.hu.inno.hulp.monoliet.testvision.domain.test.Test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubmissionTest {

    @ParameterizedTest(name = "{index} => totalPoints={0}, givenPoints={1}, expectedGrade={2}")
    @MethodSource("provideTestData")
    @DisplayName("Parameterized test for calculateGrade")
    void testCalculateGrade(int totalPoints, int givenPoints, double expectedGrade) {

        Exam exam = mock(Exam.class);
        Test test = mock(Test.class);
        Question question = mock(Question.class);


        when(exam.getTest()).thenReturn(test);
        when(test.getTotalPoints()).thenReturn(totalPoints);

        if (givenPoints > 0) {
            when(test.getQuestions()).thenReturn(List.of(question));
            when(question.getGivenPoints()).thenReturn(givenPoints);
        } else {
            when(test.getQuestions()).thenReturn(Collections.emptyList());
        }


        Submission submission = new Submission(exam);
        double grade = submission.calculateGrade();

        assertEquals(expectedGrade, grade);
    }

    private static Stream<Arguments> provideTestData() {
        return Stream.of(

                Arguments.of(100, 0, 1.0),
                Arguments.of(100, 100, 10.0),
                Arguments.of(100, 50, 5.5)
        );
    }

    @org.junit.jupiter.api.Test
    @DisplayName("When a test is sumbitted the status of the submission is SUBMITTED")
    void testSubmissionState() {
        Exam exam = mock(Exam.class);
        Submission submission = new Submission(exam);

        assertEquals(SubmissionStatus.SUBMITTED, submission.getStatus());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("When a test is graded the status of the submission is GRADED")
    void testGradedSubmissionState() {
        Exam exam = mock(Exam.class);
        Submission submission = new Submission(exam);

        submission.addGrading(new Grading());

        assertEquals(SubmissionStatus.GRADED, submission.getStatus());

    }








}

