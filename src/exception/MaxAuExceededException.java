package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when the student attempts to register a course
 * that will cause them to break their maximum vacancy ceiling.
 */
public class MaxAuExceededException extends Exception {
    /**
     * Constructs a new MaxAuExceededException with the corresponding error message.
     * @see ErrorMessage
     */
    public MaxAuExceededException() {
        super(ErrorMessage.MAX_AU_EXCEEDED);
    }
}
