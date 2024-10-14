package nl.hu.inno.hulp.exam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.hu.inno.hulp.commons.enums.ValidationStatus;
import nl.hu.inno.hulp.exam.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.exam.domain.question.OpenQuestion;
import nl.hu.inno.hulp.exam.domain.question.QuestionEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Exam {
    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    private GradingCriteria gradingCriteria;

    @Lob
    private List<Long> submissionIds = new ArrayList<>();

    private ValidationStatus validationStatus= ValidationStatus.WAITING;
    private String reason;

    @OneToMany
    private List<QuestionEntity> questions;

    private Long examValidatorId;

    @OneToOne(cascade = CascadeType.ALL)
    private Statistics statistics;

    private Long examMakerId;
    private int totalPoints;
    protected Exam(){

    }

    public Exam(Long examMakerId, Long examValidatorId){
            this.examMakerId = examMakerId;
            this.examValidatorId = examValidatorId;
            this.questions = new ArrayList<>();
            calculateTotalPoints();

    }

    public void calculateTotalPoints(){

        totalPoints = questions.stream().mapToInt(QuestionEntity::getPoints).sum();
    }

    public void removeQuestions(List<QuestionEntity> questions){
        this.questions.removeAll(questions);
    }

    public void addQuestions(List<QuestionEntity> questions){
        this.questions.addAll(questions);
    }

    public void updateStatistics() {
//        double passGrade = 5.5;
//
//        int submissionCount = submissionIds.size();
//
//        int passCount = (int) submissionIds.stream()
//                .filter(submission -> submission.calculateGrade() >= passGrade)
//                .count();
//
//        int failCount = submissionCount - passCount;
//
//        double averageScore = submissionIds.stream()
//                .mapToDouble(Submission::calculateGrade)
//                .average()
//                .orElse(0);
//
//        statistics = new Statistics(submissionCount, passCount, failCount, averageScore);
    }

    public int getTotalOpenQuestionPoints(){
        return questions.stream()
                .filter(question -> question instanceof OpenQuestion)
                .mapToInt(QuestionEntity::getPoints)
                .sum();
    }

    public int getTotalMultipleChoiceQuestionPoints(){
       return questions.stream()
                .filter(question -> question instanceof MultipleChoiceQuestion)
                .mapToInt(QuestionEntity::getPoints)
                .sum();
    }

    public void addSubmissionId(Long submissionId) {
        this.submissionIds.add(submissionId);
    }
}
