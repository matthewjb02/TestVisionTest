package nl.hu.inno.hulp.monoliet.testvision.presentation.dto;

public class AnswerRequest {
    private final Long examId;
    private final int questionNr;
    private final String answer;

    public AnswerRequest(Long examId, int questionNr, String answer) {
        this.examId = examId;
        this.questionNr = questionNr;
        this.answer = answer;
    }

    public Long getExamId() {
        return examId;
    }

    public int getQuestionNr() {
        return questionNr;
    }

    public String getAnswer() {
        return answer;
    }
}
