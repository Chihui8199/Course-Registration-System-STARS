package exception;

import errormessage.ErrorMessage;

/**
 * This is a class that extends Exception which is thrown when the LoginInfo is inaccurate.
 */
public class WrongLoginInfoException extends Exception {
    /**
     * Constructs a new WrongLoginInfoException with the corresponding error message.
     * @see ErrorMessage
     */
    public WrongLoginInfoException() {
        super(ErrorMessage.WRONG_LOGIN_INFO);
    }
}
