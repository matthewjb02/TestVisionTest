// Grading.java
package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Grading {

    @Id
    private Long id;

    private int score;
    private String comments;

    // Constructor, getters, and setters
    public Grading(int score, String comments) {
        this.score = score;
        this.comments = comments;
    }

    public Grading() {}


}