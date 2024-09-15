package nl.hu.inno.hulp.monoliet.testvision.application;

import nl.hu.inno.hulp.monoliet.testvision.data.ExamRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.State;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.ExamInactiveException;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NoExamFoundException;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.AnswerRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.ExamRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.SeeQuestion;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.StartExamRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExamServiceTest {
    private StudentService studentService;
    private TestService testService;
    private ExamRepository repository;
    private ExamService examService;
    private Exam exam;

    @BeforeEach
    @DisplayName("Get repositories and services")
    void getRepoAndService() {
        studentService = mock(StudentService.class);
        when(studentService.getStudent(1L)).thenReturn(new Student("Jan", "Steen"));

        testService = mock(TestService.class);
        Question question1 = new Question(1, "Wat is de hoofdstad van parijs.");
        Question question2 = new Question(1, "Hoe zeg je hallo in het engels.");
        nl.hu.inno.hulp.monoliet.testvision.domain.Test test =
                new nl.hu.inno.hulp.monoliet.testvision.domain.Test(question1, question2);
        when(testService.getTest(1L)).thenReturn(test);

        repository = mock(ExamRepository.class);
        examService = new ExamService(repository, studentService, testService);

        StartExamRequest startExamRequest = new StartExamRequest(1L, 1L);
        exam = examService.startExam(startExamRequest);
    }

    @Test
    @DisplayName("Start exam")
    void startExam() {
        StartExamRequest startExamRequest = new StartExamRequest(1L, 1L);
        Exam exam = examService.startExam(startExamRequest);

        assertEquals(State.Active, exam.getState());
        assertEquals("Jan", exam.getStudent().getVoornaam());
        assertEquals("Steen", exam.getStudent().getAchternaam());
        assertEquals(exam.getTest().getId(), exam.getTest().getId());
    }

    @ParameterizedTest
    @MethodSource("questions")
    @DisplayName("See Questions")
    void seeQuestions(Long examId, String questionStr, int questionNr) {
        Question question;
        SeeQuestion seeQuestion;
        when(repository.findById(1L)).thenReturn(Optional.of(exam));

        seeQuestion = new SeeQuestion(examId, questionNr);
        question = examService.seeQuestion(seeQuestion);
        assertEquals(questionStr, question.getQuestion());
    }

    public static Stream<Arguments> questions() {
        return Stream.of(
                Arguments.of(1L, "Wat is de hoofdstad van parijs.", 1),
                Arguments.of(1L, "Hoe zeg je hallo in het engels.", 2)
        );
    }

    @ParameterizedTest
    @MethodSource("answers")
    @DisplayName("Answering Questions")
    void answeringQuestions(Long examId, int questionNr, String answer) {
        AnswerRequest answerRequest;
        when(repository.findById(1L)).thenReturn(Optional.of(exam));

        answerRequest = new AnswerRequest(examId, questionNr, answer);
        exam = examService.enterAnswer(answerRequest);
        assertEquals(answer, exam.seeQuestion(questionNr).getAnswer());
    }

    public static Stream<Arguments> answers() {
        return Stream.of(
                Arguments.of(1L, 1, "is er niet"),
                Arguments.of(1L, 2, "hello")
        );
    }

    @Test
    @DisplayName("End exam")
    void EndExam() {
        when(repository.findById(1L)).thenReturn(Optional.of(exam));

        ExamRequest examRequest = new ExamRequest(1L);
        exam = examService.endExam(examRequest);
        assertEquals(State.Completed, exam.getState());
    }

    @Test
    @DisplayName("Exam Inactive With seeQuestion")
    void ExamInactiveWithSeeQuestion() {
        when(repository.findById(1L)).thenReturn(Optional.of(exam));

        ExamRequest examRequest = new ExamRequest(1L);
        examService.endExam(examRequest);

        SeeQuestion seeQuestion = new SeeQuestion(1L, 1);
        assertThrows(ExamInactiveException.class, () -> examService.seeQuestion(seeQuestion));
    }

    @Test
    @DisplayName("Exam Inactive")
    void ExamInactive() {
        when(repository.findById(1L)).thenReturn(Optional.of(exam));

        ExamRequest examRequest = new ExamRequest(1L);
        examService.endExam(examRequest);

        SeeQuestion seeQuestion = new SeeQuestion(1L, 1);
        assertThrows(ExamInactiveException.class, () -> examService.seeQuestion(seeQuestion));

        AnswerRequest answerRequest = new AnswerRequest(1L, 1, "hallo");
        assertThrows(ExamInactiveException.class, () -> examService.enterAnswer(answerRequest));
    }

    @Test
    @DisplayName("Exam Not Found")
    void ExamNotFound() {
        ExamRequest examRequest = new ExamRequest(1L);
        assertThrows(NoExamFoundException.class, () -> examService.endExam(examRequest));

        SeeQuestion seeQuestion = new SeeQuestion(1L, 1);
        assertThrows(NoExamFoundException.class, () -> examService.seeQuestion(seeQuestion));

        AnswerRequest answerRequest = new AnswerRequest(1L, 1, "hallo");
        assertThrows(NoExamFoundException.class, () -> examService.enterAnswer(answerRequest));
    }
}