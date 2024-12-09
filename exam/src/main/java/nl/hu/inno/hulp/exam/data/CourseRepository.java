package nl.hu.inno.hulp.exam.data;


import com.couchbase.client.java.query.QueryScanConsistency;
import nl.hu.inno.hulp.exam.domain.Course;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.couchbase.repository.ScanConsistency;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CouchbaseRepository<Course, String> {
    @ScanConsistency(query= QueryScanConsistency.REQUEST_PLUS)
    Course findByApprovedExamsId(String examId);

}
