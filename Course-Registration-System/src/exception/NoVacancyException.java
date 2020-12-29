package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when there is no vacancy in the particular Index Number of
 * a particular Course.
 */
public class NoVacancyException extends Exception {
    /**
     * Constructs a new NoVacancyException with the corresponding error message.
     * @see ErrorMessage
     */
    public NoVacancyException() {
        super(ErrorMessage.NO_VACANCY);
    }
}
