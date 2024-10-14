package nl.hu.inno.hulp.monoliet.testvision.data;

import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
