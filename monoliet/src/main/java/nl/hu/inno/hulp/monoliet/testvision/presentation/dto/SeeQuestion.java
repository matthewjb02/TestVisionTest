package nl.hu.inno.hulp.monoliet.testvision.presentation.dto;

public class SeeQuestion {
    private final Long examId;
    private final int questionNr;

    public SeeQuestion(Long examId, int questionNr) {
        this.examId = examId;
        this.questionNr = questionNr;
    }

    public Long getExamId() {
        return examId;
    }

    public int getQuestionNr() {
        return questionNr;
    }
}
