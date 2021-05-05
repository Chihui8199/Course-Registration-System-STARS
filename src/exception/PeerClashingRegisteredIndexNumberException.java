package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when the timetable of the registered Index Number of peer
 * clashes with the student's own index number's timetable.
 */
public class PeerClashingRegisteredIndexNumberException extends Exception {
    /**
     * Constructs a new PeerClashingRegisteredIndexNumberException with the corresponding error message.
     * @see ErrorMessage
     */
    public PeerClashingRegisteredIndexNumberException() {
        super(ErrorMessage.PEER_REGISTERED_CLASHING_INDEX);
    }
}
