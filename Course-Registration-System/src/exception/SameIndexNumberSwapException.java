package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when the student attempts to
 * swap Index Number with the same Index Number from the same Course.
 */
public class SameIndexNumberSwapException extends Exception {
    /**
     * Constructs a new SameIndexNumberSwapException with the corresponding error message.
     * @see ErrorMessage
     */
    public SameIndexNumberSwapException() {
        super(ErrorMessage.SAME_INDEX_SWAP);
    }
}
