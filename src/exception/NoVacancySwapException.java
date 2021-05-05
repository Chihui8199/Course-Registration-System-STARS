package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when there is no vacancy in the particular Index Number of
 * a particular Course that the student is trying to swap their own registered Index Number with.
 */
public class NoVacancySwapException extends Exception {
    /**
     * Constructs a new NoVacancySwapException with the corresponding error message.
     * @see ErrorMessage
     */
    public NoVacancySwapException() {
        super(ErrorMessage.NO_VACANCY_SWAP);
    }
}
