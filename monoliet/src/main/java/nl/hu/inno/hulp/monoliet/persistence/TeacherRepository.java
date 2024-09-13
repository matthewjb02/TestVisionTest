package nl.hu.inno.hulp.monoliet.persistence;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.monoliet.testvision.domain.Course;
import nl.hu.inno.hulp.monoliet.testvision.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
@Transactional
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

@Override
    Teacher save(Teacher teacher);



}
