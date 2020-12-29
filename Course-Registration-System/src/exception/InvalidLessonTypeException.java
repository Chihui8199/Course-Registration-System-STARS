package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when the lesson type is anything other than LAB, LECTURE
 * or TUTORIAL.
 */
public class InvalidLessonTypeException extends Exception {
    /**
     * Constructs a new InvalidLessonTypeException with the corresponding error message.
     * @see ErrorMessage
     */
    public InvalidLessonTypeException() {
        super(ErrorMessage.INVALID_LESSON_TYPE);
    }
}
