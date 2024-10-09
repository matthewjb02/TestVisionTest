//package nl.hu.inno.hulp.monoliet.testvision.domain;
//
//import nl.hu.inno.hulp.exam.domain.question.Question;
//import nl.hu.inno.hulp.grading.Submission;
//import nl.hu.inno.hulp.monoliet.testvision.domain.exam.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//
//import java.util.List;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class ExamTest {
//
//    private Exam exam;
//    private Question question1;
//    private Question question2;
//
////    @BeforeEach
////    void createTest() {
////        question1 = new Question(5, "Question 1");
////        question2 = new Question(10, "Question 2");
////        exam = new Exam("maker@example.com", "validator@example.com", question1, question2);
////    }
//
//    @Test
//    @DisplayName("Correctly calculated total points")
//    void testCalculateTotalPoints() {
//        assertEquals(15, exam.getTotalPoints());
//    }
//
//    @Test
//    @DisplayName("Questions can be added and removed")
//    void testAddAndRemoveQuestions() {
//        Question question3 = new Question(20, "Question 3");
//
//        exam.addQuestions(List.of(question3));
//        assertEquals(3, exam.getQuestions().size());
//
//        exam.removeQuestions(List.of(question3));
//        assertEquals(2, exam.getQuestions().size());
//    }
//
////    @Test
////    @DisplayName("Validations are set correctly")
////    void testSetAndGetValidationStatus() {
////        assertEquals(ValidationStatus.WAITING, exam.getValidationStatus());
////
////        exam.setValidationStatus(ValidationStatus.APPROVED);
////        assertEquals(ValidationStatus.APPROVED, exam.getValidationStatus());
////    }
//
//    @Test
//    @DisplayName("You can add grading criteria")
//    void testAddGradingCriteria() {
//        GradingCriteria criteria = new GradingCriteria();
//        exam.addGradingCriteria(criteria);
//        assertEquals(criteria, exam.getGradingCriteria());
//    }
//
//    @Test
//    @DisplayName("You can add submissions")
//    void testAddSubmission() {
//        Submission submission = new Submission();
//        exam.addSubmission(submission);
//        assertEquals(1, exam.getSubmissions().size());
//    }
//
//    @Test
//    @DisplayName("You can add statistics")
//    void testAddStatistics() {
//        Statistics stats = new Statistics(1, 1, 0, 1);
//        exam.addStatistics(stats);
//        assertEquals(stats, exam.getStatistics());
//    }
//
//    // Added tests by Matthew
//    @ParameterizedTest(name = "{index} => grades={0}, expectedPassCount={1}, expectedFailCount={2}, expectedAverage={3}")
//    @MethodSource("provideTestData")
//    @DisplayName("Parameterized test for updateStatistics")
//    void testUpdateStatistics(List<Double> grades, int expectedPassCount, int expectedFailCount, double expectedAverage) {
//
//        Exam exam = new Exam();
//        Submission submission = mock(Submission.class);
//
//
//        for (double grade : grades) {
//            Submission mockSubmission = mock(Submission.class);
//            when(mockSubmission.calculateGrade()).thenReturn(grade);
//            exam.addSubmission(mockSubmission);
//        }
//
//
//        exam.updateStatistics();
//
//        Statistics statistics = exam.getStatistics();
//
//        assertEquals(grades.size(), statistics.getSubmissionCount());
//        assertEquals(expectedPassCount, statistics.getPassCount());
//        assertEquals(expectedFailCount, statistics.getFailCount());
//        assertEquals(expectedAverage, statistics.getAverageScore(), 0.01);
//    }
//
//    private static Stream<Arguments> provideTestData() {
//        return Stream.of(
//
//                Arguments.of(List.of(6.0, 7.0, 8.0), 3, 0, 7.0),
//                Arguments.of(List.of(4.0, 5.0, 6.0), 1, 2, 5.0),
//                Arguments.of(List.of(5.5, 5.4, 5.6), 2, 1, 5.5),
//                Arguments.of(List.of(3.0, 2.0, 1.0), 0, 3, 2.0)
//        );
//    }
//}
//
//
