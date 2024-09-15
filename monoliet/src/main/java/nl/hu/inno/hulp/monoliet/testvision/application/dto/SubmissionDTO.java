// In de SubmissionDTO klasse
package nl.hu.inno.hulp.monoliet.testvision.application.dto;

import nl.hu.inno.hulp.monoliet.testvision.domain.SubmissionStatus;

public class SubmissionDTO {

    private Long id;
    private SubmissionStatus status;

    public SubmissionDTO(Long id, SubmissionStatus status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public SubmissionStatus getStatus() {
        return status;
    }
}