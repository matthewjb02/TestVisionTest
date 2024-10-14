package nl.hu.inno.hulp.monoliet.testvision.data;

import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
}
