package nl.hu.inno.hulp.monoliet.testvision.presentation.dto;

import nl.hu.inno.hulp.monoliet.testvision.application.TestDTO;
import nl.hu.inno.hulp.monoliet.testvision.domain.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.State;

public class ExamResponse {
    private final StudentResponse student;
    private final TestDTO test;
    private final State state;

    public ExamResponse(Exam exam) {
        this.student = new StudentResponse(exam.getStudent());
        this.test = new TestDTO(exam.getTest().getId(),
                                exam.getTest().getQuestions(),
                                exam.getTest().getTotalPoints());
        this.state = exam.getState();
    }

    public StudentResponse getStudent() {
        return student;
    }

    public TestDTO getTest() {
        return test;
    }

    public State getState() {
        return state;
    }
}
