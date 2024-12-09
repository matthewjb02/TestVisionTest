package nl.hu.inno.hulp.exam.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Scope;

import java.util.UUID;

@Document
@Getter
@Scope("statistics")
@Collection("statistics")
public class Statistics {

    @Id

    private String id= UUID.randomUUID().toString();
    @Field
    private int submissionCount;
    @Field
    private int passCount;
    @Field
    private int failCount;
    @Field
    private double averageScore;


    public Statistics(int submissionCount, int passCount, int failCount, double averageScore) {
        this.submissionCount = submissionCount;
        this.passCount = passCount;
        this.failCount = failCount;
        this.averageScore = averageScore;
    }

    protected Statistics() {
    }

    public static Statistics createStatistics(int submissionCount, int passCount, int failCount, double averageScore) {
        return new Statistics(submissionCount, passCount, failCount, averageScore);
    }


}