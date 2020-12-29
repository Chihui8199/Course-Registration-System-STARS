package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when the course the student is trying to register for
 * is already registered for them.
 */
public class CourseRegisteredException extends Exception {
    /**
     * Constructs a new CourseRegisteredException with the corresponding error message.
     * @see ErrorMessage
     */
    public CourseRegisteredException() {
        super(ErrorMessage.COURSE_ALREADY_REGISTERED);
    }
}
