package exception;

import errormessage.ErrorMessage;
import model.AccessTime;

/**
 * This is a class that extends Exception which is thrown when the course the student is trying to access STARS
 * Application outside of their access period.
 */
public class WrongAccessPeriodException extends Exception {
    /**
     * Constructs a new WrongAccessPeriodException with the corresponding error message.
     * @param accessTime The {@code AccessTime} object containing the correct access time for the student.
     * @see ErrorMessage
     */
    public WrongAccessPeriodException(AccessTime accessTime) {
        super(ErrorMessage.WRONG_ACCESS_PERIOD + "\n" + accessTime.toString());
    }
}
