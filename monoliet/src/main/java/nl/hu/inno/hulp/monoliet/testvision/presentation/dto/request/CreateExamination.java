package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request;

public record CreateExamination(String name, Long examId, String password, ExamDateDTO examDate, int duration, int extraTime) {
}
