package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when the maximum vacancy for a particular Index Number of a
 * particular Course is less than 1 or contains special characters.
 */
public class InvalidNewMaxException extends Exception {
    /**
     * Constructs a new InvalidNewMaxException with the corresponding error message.
     * @see ErrorMessage
     */
    public InvalidNewMaxException() {
        super(ErrorMessage.INVALID_NEW_MAX_VACANCY);
    }
}
