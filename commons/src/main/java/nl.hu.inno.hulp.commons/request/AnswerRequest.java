package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request;

public record AnswerRequest(Long examSessionId, int questionNr, Object answer) {
}
