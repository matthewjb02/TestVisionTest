// In de SubmissionDTO klasse
package nl.hu.inno.hulp.commons.dto;

import nl.hu.inno.hulp.commons.enums.SubmissionStatus;

public record SubmissionDTO(Long id, SubmissionStatus status) {

}