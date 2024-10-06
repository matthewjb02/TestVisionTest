package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;

import java.util.List;

public class ExamResponse {
    private final Long id;
    private final int totalPoints;
    private final List<QuestionEntity> questions;

    public ExamResponse(Exam exam) {
        this.id = exam.getId();
        this.totalPoints = exam.getTotalPoints();
        this.questions = exam.getQuestions();
    }

    public Long getId() {
        return id;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }
}
