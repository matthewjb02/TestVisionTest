package nl.hu.inno.hulp.monoliet.testvision.presentation.dto;

import nl.hu.inno.hulp.monoliet.testvision.domain.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.State;
import nl.hu.inno.hulp.monoliet.testvision.domain.Test;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;

public class ExamResponse {
    private final Student student;
    private final Test test;
    private final State state;

    public ExamResponse(Exam exam) {
        this.student = exam.getStudent();
        this.test = exam.getTest();
        this.state = exam.getState();
    }

    public Student getStudent() {
        return student;
    }

    public Test getTest() {
        return test;
    }

    public State getState() {
        return state;
    }
}
