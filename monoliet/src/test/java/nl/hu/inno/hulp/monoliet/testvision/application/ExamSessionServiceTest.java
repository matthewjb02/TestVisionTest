//package nl.hu.inno.hulp.monoliet.testvision.application;
//
//import nl.hu.inno.hulp.monoliet.testvision.application.service.ExamSessionService;
//import nl.hu.inno.hulp.monoliet.testvision.application.service.ExaminationService;
//import nl.hu.inno.hulp.monoliet.testvision.application.service.StudentService;
//import nl.hu.inno.hulp.monoliet.testvision.application.service.ExamService;
//import nl.hu.inno.hulp.monoliet.testvision.data.ExamSessionRepository;
//import nl.hu.inno.hulp.monoliet.testvision.data.ExaminationRepository;
//import nl.hu.inno.hulp.monoliet.testvision.data.SubmissionRepository;
//import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
//import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamSession;
//import nl.hu.inno.hulp.monoliet.testvision.domain.examination.Examination;
//import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NoExamSessionFoundException;
//import nl.hu.inno.hulp.monoliet.testvision.domain.exception.PasswordIncorrectException;
//import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
//import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
//import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamState;
//import nl.hu.inno.hulp.monoliet.testvision.domain.exception.ExaminationInactiveException;
//import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NoExaminationFoundException;
//import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
//import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class ExamSessionServiceTest {
//    private StudentService studentService;
//    private ExamService examService;
//    private ExaminationRepository examinationRepository;
//    private ExaminationService examinationService;
//    private ExamSessionRepository examSessionRepository;
//    private ExamSessionService examSessionService;
//    private SubmissionRepository submissionRepository;
//    private Examination examination;
//    private ExamSession examSession;
//    private Exam exam;
//
////    @BeforeEach
////    @DisplayName("Get repositories and services")
////    void getRepoAndService() {
////        studentService = mock(StudentService.class);
////        Student student = new Student("Jan", "Steen", false);
////        when(studentService.getStudent(1L)).thenReturn(student);
////
////        examService = mock(ExamService.class);
////        Question question1 = new OpenQuestion(1, "Wat is de hoofdstad van parijs.", "is er niet");
////        Question question2 = new OpenQuestion(1, "Hoe zeg je hallo in het engels.", "Hello");
////        exam = new Exam("", "", question1, question2);
////        when(examService.getExam(1L)).thenReturn(exam);
////
////        examinationRepository = mock(ExaminationRepository.class);
////        examinationService = new ExaminationService(examinationRepository, examSessionRepository, studentService, examService);
////        examination = new Examination("toets1", exam,"hallo", null, 120, 30);
////        examination.selectCandidates(List.of(student));
////        when(examinationRepository.findById(1L)).thenReturn(Optional.of(examination));
////
////        examSessionRepository = mock(ExamSessionRepository.class);
////        submissionRepository = mock(SubmissionRepository.class);
////        examSessionService = new ExamSessionService(examSessionRepository, studentService, examinationService, submissionRepository);
////
////        StartExamSession startExamSession = new StartExamSession(1L, 1L, "hallo");
////        examSession = examSessionService.startExamSession(startExamSession);
////    }
//
//    @Test
//    @DisplayName("Start exam")
//    void startExam() {
//        StartExamSession startExamSession = new StartExamSession(1L, 1L, "hallo");
//        ExamSession examSession = examSessionService.startExamSession(startExamSession);
//
//        assertEquals(ExamState.Active, examSession.getState());
//        assertEquals("Jan", examSession.getStudent().getFirstName());
//        assertEquals("Steen", examSession.getStudent().getLastName());
//        assertEquals(examSession.getExam().getId(), examSession.getExam().getId());
//    }
//
//    @Test
//    @DisplayName("Wrong password")
//    void WrongPassword() {
//        StartExamSession startExamSession = new StartExamSession(1L, 1L, "hi");
//        assertThrows(PasswordIncorrectException.class, () -> examSessionService.startExamSession(startExamSession));
//    }
//
//    @ParameterizedTest
//    @MethodSource("questions")
//    @DisplayName("See Questions")
//    void seeQuestions(Long examId, String questionStr, int questionNr) {
//        Question question;
//        SeeQuestion seeQuestion;
//        when(examSessionRepository.findById(1L)).thenReturn(Optional.of(examSession));
//
//        seeQuestion = new SeeQuestion(examId, questionNr);
//        question = examSessionService.seeQuestion(seeQuestion);
//        assertEquals(questionStr, question.getQuestion());
//    }
//
//    public static Stream<Arguments> questions() {
//        return Stream.of(
//                Arguments.of(1L, "Wat is de hoofdstad van parijs.", 1),
//                Arguments.of(1L, "Hoe zeg je hallo in het engels.", 2)
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("answers")
//    @DisplayName("Answering Questions")
//    void answeringQuestions(Long examId, int questionNr, String answer) {
//        AnswerRequest answerRequest;
//        when(examSessionRepository.findById(1L)).thenReturn(Optional.of(examSession));
//
//        answerRequest = new AnswerRequest(examId, questionNr, answer);
//        examSession = examSessionService.enterAnswer(answerRequest);
//        OpenQuestion question = (OpenQuestion) examSession.seeQuestion(questionNr);
//        assertEquals(answer, question.getAnswer());
//    }
//
//    public static Stream<Arguments> answers() {
//        return Stream.of(
//                Arguments.of(1L, 1, "is er niet"),
//                Arguments.of(1L, 2, "hello")
//        );
//    }
//
//    @Test
//    @DisplayName("End exam")
//    void EndExam() {
//        when(examSessionRepository.findById(1L)).thenReturn(Optional.of(examSession));
//
//        ExamSessionRequest examSessionRequest = new ExamSessionRequest(1L);
//        examSession = examSessionService.endExamSession(examSessionRequest);
//        assertEquals(ExamState.Completed, examSession.getState());
//    }
//
//    @Test
//    @DisplayName("Exam Inactive With seeQuestion")
//    void ExamInactiveWithSeeQuestion() {
//        when(examSessionRepository.findById(1L)).thenReturn(Optional.of(examSession));
//
//        ExamSessionRequest examSessionRequest = new ExamSessionRequest(1L);
//        examSession = examSessionService.endExamSession(examSessionRequest);
//
//        SeeQuestion seeQuestion = new SeeQuestion(1L, 1);
//        assertThrows(ExaminationInactiveException.class, () -> examSessionService.seeQuestion(seeQuestion));
//    }
//
//    @Test
//    @DisplayName("Exam Inactive")
//    void ExamInactive() {
//        when(examSessionRepository.findById(1L)).thenReturn(Optional.of(examSession));
//
//        ExamSessionRequest examSessionRequest = new ExamSessionRequest(1L);
//        examSessionService.endExamSession(examSessionRequest);
//
//        SeeQuestion seeQuestion = new SeeQuestion(1L, 1);
//        assertThrows(ExaminationInactiveException.class, () -> examSessionService.seeQuestion(seeQuestion));
//
//        AnswerRequest answerRequest = new AnswerRequest(1L, 1, "hallo");
//        assertThrows(ExaminationInactiveException.class, () -> examSessionService.enterAnswer(answerRequest));
//    }
//
//    @Test
//    @DisplayName("Exam Not Found")
//    void ExamNotFound() {
//        ExamSessionRequest examinationRequest = new ExamSessionRequest(1L);
//        assertThrows(NoExamSessionFoundException.class, () -> examSessionService.endExamSession(examinationRequest));
//
//        SeeQuestion seeQuestion = new SeeQuestion(1L, 1);
//        assertThrows(NoExamSessionFoundException.class, () -> examSessionService.seeQuestion(seeQuestion));
//
//        AnswerRequest answerRequest = new AnswerRequest(1L, 1, "hallo");
//        assertThrows(NoExamSessionFoundException.class, () -> examSessionService.enterAnswer(answerRequest));
//    }
//}