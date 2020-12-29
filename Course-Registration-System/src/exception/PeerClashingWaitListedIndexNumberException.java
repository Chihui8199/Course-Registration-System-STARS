package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when the timetable of the waitlisted Index Number of peer
 * clashes with the student's own index number's timetable.
 */
public class PeerClashingWaitListedIndexNumberException extends Exception {
    /**
     * Constructs a new PeerClashingWaitListedIndexNumberException with the corresponding error message.
     * @see ErrorMessage
     */
    public PeerClashingWaitListedIndexNumberException() {
        super(ErrorMessage.PEER_WAITLISTED_CLASHING_INDEX);
    }
}
