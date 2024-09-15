// In de SubmissionDTO klasse
package nl.hu.inno.hulp.monoliet.testvision.application.dto;

import nl.hu.inno.hulp.monoliet.testvision.domain.test.SubmissionStatus;

public record SubmissionDTO(Long id, SubmissionStatus status) {

}