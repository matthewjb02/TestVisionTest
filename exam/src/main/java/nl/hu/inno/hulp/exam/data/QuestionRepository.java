package nl.hu.inno.hulp.exam.data;

import nl.hu.inno.hulp.exam.domain.question.OpenQuestion;
import nl.hu.inno.hulp.exam.domain.question.QuestionEntity;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CouchbaseRepository<QuestionEntity, String> {
    @Query("""
        SELECT * 
        FROM `exam`.question.openQuestion USE KEYS $1
        UNION ALL
        SELECT * 
        FROM `exam`.question.multipleChoiceQuestion USE KEYS $1
    """)
    QuestionEntity findQuestionsById(String id);
}
