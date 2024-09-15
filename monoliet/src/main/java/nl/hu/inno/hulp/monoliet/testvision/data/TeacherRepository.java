package nl.hu.inno.hulp.monoliet.testvision.data;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.monoliet.testvision.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
