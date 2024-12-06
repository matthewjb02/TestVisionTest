package nl.hu.inno.hulp.exam.data;

import nl.hu.inno.hulp.exam.domain.question.QuestionEntity;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CouchbaseRepository<QuestionEntity, String> {
}
