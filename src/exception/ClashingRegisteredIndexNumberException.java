package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when there is a clash between registered index numbers
 * and the index number a student is currently trying to register.
 */
public class ClashingRegisteredIndexNumberException extends Exception {
    /**
     * Constructs a new ClashingRegisteredIndexNumberException with the corresponding error message.
     * @see ErrorMessage
     */
    public ClashingRegisteredIndexNumberException() {
        super(ErrorMessage.CLASHING_REGISTERED_INDEX_NUMBER);
    }
}
