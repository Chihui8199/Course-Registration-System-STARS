package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when there is a clash between waitlisted index numbers
 * and the index number a student is currently trying to register.
 */
public class ClashingWaitListedIndexNumberException extends Exception {
    /**
     * Constructs a new ClashingWaitListedIndexNumberException with the corresponding error message.
     * @see ErrorMessage
     */
    public ClashingWaitListedIndexNumberException() {
        super(ErrorMessage.CLASHING_WAITLISTED_INDEX_NUMBER);
    }
}
