package nl.hu.inno.hulp.monoliet.testvision.application;

import nl.hu.inno.hulp.monoliet.testvision.application.service.ExaminationService;
import nl.hu.inno.hulp.monoliet.testvision.application.service.StudentService;
import nl.hu.inno.hulp.monoliet.testvision.application.service.TestService;
import nl.hu.inno.hulp.monoliet.testvision.data.ExaminationRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.SubmissionRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.Examination;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.examination.State;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.ExaminationInactiveException;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NoExaminationFoundException;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.AnswerRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.ExaminationRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.SeeQuestion;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.StartExaminationRequest;
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

public class ExaminationServiceTest {
    private StudentService studentService;
    private TestService testService;
    private ExaminationRepository repository;
    private ExaminationService examinationService;
    private Examination examination;
    private SubmissionRepository submissionRepository;

    @BeforeEach
    @DisplayName("Get repositories and services")
    void getRepoAndService() {
        studentService = mock(StudentService.class);
        when(studentService.getStudent(1L)).thenReturn(new Student("Jan", "Steen"));

        testService = mock(TestService.class);
        Question question1 = new OpenQuestion(1, "Wat is de hoofdstad van parijs.", "is er niet");
        Question question2 = new OpenQuestion(1, "Hoe zeg je hallo in het engels.", "Hello");
        nl.hu.inno.hulp.monoliet.testvision.domain.test.Test test =
                new nl.hu.inno.hulp.monoliet.testvision.domain.test.Test("", "", question1, question2);
        when(testService.getTest(1L)).thenReturn(test);

        repository = mock(ExaminationRepository.class);
        submissionRepository = mock(SubmissionRepository.class);
        examinationService = new ExaminationService(repository, studentService, testService, submissionRepository);

        StartExaminationRequest startExaminationRequest = new StartExaminationRequest(1L, 1L);
        examination = examinationService.startExamination(startExaminationRequest);
    }

    @Test
    @DisplayName("Start exam")
    void startExam() {
        StartExaminationRequest startExaminationRequest = new StartExaminationRequest(1L, 1L);
        Examination examination = examinationService.startExamination(startExaminationRequest);

        assertEquals(State.Active, examination.getState());
        assertEquals("Jan", examination.getStudent().getFirstName());
        assertEquals("Steen", examination.getStudent().getLastName());
        assertEquals(examination.getTest().getId(), examination.getTest().getId());
    }

    @ParameterizedTest
    @MethodSource("questions")
    @DisplayName("See Questions")
    void seeQuestions(Long examId, String questionStr, int questionNr) {
        Question question;
        SeeQuestion seeQuestion;
        when(repository.findById(1L)).thenReturn(Optional.of(examination));

        seeQuestion = new SeeQuestion(examId, questionNr);
        question = examinationService.seeQuestion(seeQuestion);
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
        when(repository.findById(1L)).thenReturn(Optional.of(examination));

        answerRequest = new AnswerRequest(examId, questionNr, answer);
        examination = examinationService.enterAnswer(answerRequest);
        OpenQuestion question = (OpenQuestion) examination.seeQuestion(questionNr);
        assertEquals(answer, question.getAnswer());
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
        when(repository.findById(1L)).thenReturn(Optional.of(examination));

        ExaminationRequest examinationRequest = new ExaminationRequest(1L);
        examination = examinationService.endExam(examinationRequest);
        assertEquals(State.Completed, examination.getState());
    }

    @Test
    @DisplayName("Exam Inactive With seeQuestion")
    void ExamInactiveWithSeeQuestion() {
        when(repository.findById(1L)).thenReturn(Optional.of(examination));

        ExaminationRequest examinationRequest = new ExaminationRequest(1L);
        examinationService.endExam(examinationRequest);

        SeeQuestion seeQuestion = new SeeQuestion(1L, 1);
        assertThrows(ExaminationInactiveException.class, () -> examinationService.seeQuestion(seeQuestion));
    }

    @Test
    @DisplayName("Exam Inactive")
    void ExamInactive() {
        when(repository.findById(1L)).thenReturn(Optional.of(examination));

        ExaminationRequest examinationRequest = new ExaminationRequest(1L);
        examinationService.endExam(examinationRequest);

        SeeQuestion seeQuestion = new SeeQuestion(1L, 1);
        assertThrows(ExaminationInactiveException.class, () -> examinationService.seeQuestion(seeQuestion));

        AnswerRequest answerRequest = new AnswerRequest(1L, 1, "hallo");
        assertThrows(ExaminationInactiveException.class, () -> examinationService.enterAnswer(answerRequest));
    }

    @Test
    @DisplayName("Exam Not Found")
    void ExamNotFound() {
        ExaminationRequest examinationRequest = new ExaminationRequest(1L);
        assertThrows(NoExaminationFoundException.class, () -> examinationService.endExam(examinationRequest));

        SeeQuestion seeQuestion = new SeeQuestion(1L, 1);
        assertThrows(NoExaminationFoundException.class, () -> examinationService.seeQuestion(seeQuestion));

        AnswerRequest answerRequest = new AnswerRequest(1L, 1, "hallo");
        assertThrows(NoExaminationFoundException.class, () -> examinationService.enterAnswer(answerRequest));
    }
}