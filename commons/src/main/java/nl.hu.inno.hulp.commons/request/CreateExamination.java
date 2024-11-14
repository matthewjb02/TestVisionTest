package nl.hu.inno.hulp.commons.request;

public record CreateExamination(String name,Long courseId, Long examId, String password, ExamDateDTO examDate, int duration, int extraTime) {
}
