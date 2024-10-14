package nl.hu.inno.hulp.examination.data;

import nl.hu.inno.hulp.examination.domain.ExamSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {
}
