package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request;

import nl.hu.inno.hulp.monoliet.testvision.domain.examination.ExamDate;

public record CreateExamination(String name, Long examId, String password, ExamDate examDate, int duration, int extraTime) {
}
