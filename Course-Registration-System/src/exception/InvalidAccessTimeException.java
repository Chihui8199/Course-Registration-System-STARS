package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when the course the student is trying to access STARS
 * Application past their end date and time.
 */
public class InvalidAccessTimeException extends IllegalArgumentException {
    /**
     * Constructs a new InvalidAccessTimeException with the corresponding error message.
     * @see ErrorMessage
     */
    public InvalidAccessTimeException() {
        super(ErrorMessage.INVALID_ACCESS_TIME);
    }
}
