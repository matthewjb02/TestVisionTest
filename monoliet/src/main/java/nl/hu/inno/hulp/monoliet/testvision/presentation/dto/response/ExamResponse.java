package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response;

import lombok.Getter;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ExamResponse {
    private final Long id;
    private final int totalPoints;
    private final List<QuestionResponse> questions;

    public ExamResponse(Exam exam) {
        this.id = exam.getId();
        this.totalPoints = exam.getTotalPoints();

        this.questions = new ArrayList<>();
        for (QuestionEntity question : exam.getQuestions()){
            questions.add(new QuestionResponse(question));
        }
    }
}
