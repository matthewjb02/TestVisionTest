package nl.hu.inno.hulp.monoliet.testvision.data;

import nl.hu.inno.hulp.monoliet.testvision.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
