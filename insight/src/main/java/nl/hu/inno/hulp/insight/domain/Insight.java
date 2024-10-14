package nl.hu.inno.hulp.insight.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import nl.hu.inno.hulp.commons.response.SubmissionResponse;

import java.util.List;

@Entity
public class Insight {

    @Id
    @GeneratedValue
    private Long id;
    @
    private List<SubmissionResponse> submissions;





    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
