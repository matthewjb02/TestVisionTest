package nl.hu.inno.hulp.monoliet.testvision.data;

import nl.hu.inno.hulp.monoliet.testvision.domain.test.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}
