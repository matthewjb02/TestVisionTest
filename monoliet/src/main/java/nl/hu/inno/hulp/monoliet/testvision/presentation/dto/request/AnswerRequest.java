package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request;

public record AnswerRequest(Long examSessionId, Long studentId, int questionNr, Object answer) {
}
