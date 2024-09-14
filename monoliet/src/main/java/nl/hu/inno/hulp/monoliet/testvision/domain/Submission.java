// Submission.java
package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Submission {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private TestAttempt testAttempt;


    @OneToOne
    private Grading grading;

    // Constructor, getters, and setters
    public Submission(TestAttempt testAttempt, Grading grading) {
        this.testAttempt = testAttempt;

        this.grading = grading;
    }

    public Submission() {
    }
}
