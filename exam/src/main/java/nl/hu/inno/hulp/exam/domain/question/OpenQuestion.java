package nl.hu.inno.hulp.exam.domain.question;

import jakarta.persistence.Entity;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Scope;

@Document
@Scope("question")
@Collection("openQuestion")
public class OpenQuestion extends QuestionEntity {
    @Field
    private String correctAnswer;
    @Field
    private String answer = "";
    @Field
    private String teacherFeedback = "";

    protected OpenQuestion() {
    }

    public OpenQuestion(int points, String question, String correctAnswer) {
        super(points, question);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public void addGivenPoints(int points) {
        this.givenPoints += points;
    }

    @Override
    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTeacherFeedback() {
        return teacherFeedback;
    }

    public void setTeacherFeedback(String feedback) {
        this.teacherFeedback = feedback;
    }

    public void addTeacherFeedback(String feedback) {
        this.teacherFeedback = feedback;
    }
}