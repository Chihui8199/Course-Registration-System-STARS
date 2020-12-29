package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when the course the student is trying to register for
 * is already in wait-list.
 */
public class CourseInWaitListException extends Exception {
    /**
     * Constructs a new CourseInWaitListException with the corresponding error message.
     * @see ErrorMessage
     */
    public CourseInWaitListException() {
        super(ErrorMessage.COURSE_ALREADY_IN_WAITLIST);
    }
}
