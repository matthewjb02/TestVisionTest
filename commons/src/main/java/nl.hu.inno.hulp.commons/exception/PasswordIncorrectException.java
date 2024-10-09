package nl.hu.inno.hulp.monoliet.testvision.domain.exception;

public class PasswordIncorrectException extends RuntimeException {
    public PasswordIncorrectException(String message) {
        super(message);
    }
}
