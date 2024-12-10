package nl.hu.inno.hulp.exam.data;

import nl.hu.inno.hulp.exam.domain.Exam;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends CouchbaseRepository<Exam, String> {
}
