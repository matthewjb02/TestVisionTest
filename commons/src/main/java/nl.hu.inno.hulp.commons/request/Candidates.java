package nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request;

import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;

import java.util.List;

public class Candidates {
    public Long examinationId;
    public List<Student> students;
}
