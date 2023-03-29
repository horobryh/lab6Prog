package general.validators.exceptions;

public abstract class ValidatorException extends Exception {
    public ValidatorException(String message) {
        super(message);
    }
}
