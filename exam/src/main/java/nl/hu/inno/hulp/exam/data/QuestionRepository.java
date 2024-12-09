package nl.hu.inno.hulp.exam.data;

import nl.hu.inno.hulp.exam.domain.question.OpenQuestion;
import nl.hu.inno.hulp.exam.domain.question.QuestionEntity;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CouchbaseRepository<QuestionEntity, String> {
    @Query("SELECT * FROM exam.question.openQuestion use keys 1$")
    OpenQuestion findOpenQuestionById(String id);
    @Query("SELECT RAW e FROM exam.question.openQuestion AS e WHERE META(e).id = 1$")
    String findRawOpenQuestionById( String id);
}
